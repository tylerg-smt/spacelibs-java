package com.siliconmtn.data.report;

// JDK 1.8
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

// Apache commons 3.x
import org.apache.commons.lang3.StringUtils;

// Apache POI 3.13
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

// Space Libs 1.x
import com.siliconmtn.data.format.BooleanUtil;
import com.siliconmtn.data.format.DateFormat;
import com.siliconmtn.data.format.NumberUtil;
import com.siliconmtn.data.format.DateFormat.DatePattern;
import com.siliconmtn.data.report.ExcelStyleFactory.Styles;

/****************************************************************************
 * <b>Title</b>: ExcelReport.java<p/>
 * <b>Project</b>: SpaceLibs-Java<p/>
 * <b>Description: takes a passed map of data and builds an Excel file dynamically.
 * The constructor takes the headerMap, which is used for column headings and 
 * to align the rows (responses).</b> 
 * <p/>
 * <b>Copyright:</b> Copyright (c) 2021<p/>
 * <b>Company:</b> Silicon Mountain Technologies<p/>
 * @author James Camire
 * @version 1.0
 * @since Feb 14, 2021
 * @updates
 ****************************************************************************/

public class ExcelReport extends AbstractReport {
	/**
	 * Apache Cell Types lack important types.  Assign a cell value tye for 
	 * each cell so we can format the cell as desired 
	 */
	public enum CellValueType {
		BOOLEAN, DOUBLE, DATE, INTEGER, STRING, TIMESTAMP;
	}
	
	/**
	 * Sets the maximum allowable rows per sheet
	 */
	public static final int MAX_ROWS_PER_SHEET = 64000;

	// Members
	private static final long serialVersionUID = 1l;
	protected transient Collection<Map<String, Object>> rowData;
	protected transient Map<String, String> headerMap;
	protected String titleText;
	protected transient Workbook wb;
	protected transient CellStyle headerStyle;
	protected transient CellStyle titleStyle;
	protected transient CellStyle bodyStyle;
	protected transient CellStyle dateStyle;
	protected transient CellStyle tempDateStyle;
	protected boolean expandColumnFlag;
	protected boolean displayDate;
	protected int maxRowsPerSheet = MAX_ROWS_PER_SHEET;

	/**
	 * Constructor utilizes the NoStyle formatting
	 * @param headerMap
	 */
	public ExcelReport(Map<String, String> headerMap) {
		this(headerMap, ExcelStyleFactory.getExcelStyle(Styles.NO_STYLE));
	}
	
	/**
	 * Constructor utilizes the NoStyle formatting
	 * @param headerMap
	 */
	public ExcelReport(Map<String, String> headerMap, Styles s) {
		this(headerMap, ExcelStyleFactory.getExcelStyle(s));
	}

	/**
	 * Excel report using the provided style
	 * @param headerMap Map of the id and name for each column heading
	 * @param style
	 */
	public ExcelReport(Map<String, String> headerMap, ExcelStyleInterface style) {
		super();
		wb = new XSSFWorkbook();
		this.headerMap = headerMap;
		headerStyle = style.getHeadingStyle(wb);
		titleStyle = style.getTitleStyle(wb);
		bodyStyle =   style.getBodyStyle(wb);
		dateStyle = style.getDateStyle(wb);
		expandColumnFlag = style.getExpandColumnFlag();
		displayDate = style.displayDate();
		tempDateStyle = wb.createCellStyle();
		CreationHelper createHelper = wb.getCreationHelper();
		tempDateStyle.setDataFormat(createHelper.createDataFormat().getFormat(DatePattern.DATETIME_SLASH_SHORT.getPattern()));
	}

	/* (non-Javadoc)
	 * @see com.siliconmtn.data.report.SMTReportVO#generateReport()
	 */
	@Override
	public byte[] generateReport() throws IOException {
		createSheet(null, titleText, headerMap, rowData);
		return getBytes(wb);
	}

	/**
	 * Create a sheet in the Workbook using the passed args
	 * @param title
	 * @param header
	 */
	protected void createSheet(String sheetName, String title,Map<String, String> header, Collection<Map<String, Object>> rows) {
		Sheet s = buildSheet(sheetName, title, header);
		int count = 0;
		int sheetCount = 1;
		Row row;
		
		//loop the data rows
		for (Map<String, Object> lineData : rows) {
			// If the rows exceeds the max, create a new sheet.  Resize columns on 
			// initial sheet if requested
			if (count > maxRowsPerSheet) {
				resizeSheetColumns(s, header, rows.size());
				s = buildSheet(sheetName + " - " + sheetCount++, title, header);
			}

			//add the row data
			row = s.createRow(s.getPhysicalNumberOfRows());
			addRowData(row, lineData, header);
			count++;
		}

		resizeSheetColumns(s, header, rows.size());
	}
	
	/*
	 * 	Making the cell more readable by expanding the cell widths. NOTE: This 
	 *  column resizing can be very costly on larger reports. Calculate a cost
	 *  based on rows*columns, and don't resize columns on larger reports.  
	 *  An 8500-row report was taking 14s to resize!
	 */
	protected void resizeSheetColumns(Sheet s, Map<String, String> header, int rowCount) {
		int cost = rowCount * s.getLastRowNum();
		
		if (expandColumnFlag && cost < 50000) {
			for (int i = 0; i < header.size(); i++)
				s.autoSizeColumn(i);
		}
	}

	/**
	 * Manage creating a sheet for the workbook.
	 * Will generate header row at the top of the sheet.
	 * @param sheetName
	 * @param title
	 * @param header
	 * @return
	 */
	private Sheet buildSheet(String sheetName, String title, Map<String, String> header) {
		//Create Excel Sheet inside the Workbook
		Sheet s = wb.createSheet();

		//name the sheet if desired
		if (!StringUtils.isEmpty(sheetName)) wb.setSheetName(wb.getSheetIndex(s), sheetName);

		//if requested add title row
		if (!StringUtils.isEmpty(title)) addTitleCell(s, title, header.keySet().size());

		// if requested, add current date
		if (displayDate) addDateRow(s, header.keySet().size());

		// add the header row
		addHeaderRow(s, header);

		return s;
	}

	/**
	 * Adds the header row to the excel spreadsheet
	 * @param s
	 * @param header
	 */
	protected void addHeaderRow(Sheet s, Map<String, String> header) {
		Row row = s.createRow(s.getPhysicalNumberOfRows());

		//Loop and set cell values for the header row.
		int cellCnt = 0;
		for (Map.Entry<String, String> entry : header.entrySet()) {
			Cell c = row.createCell(cellCnt++);
			c.setCellValue(entry.getValue());
			c.setCellStyle(headerStyle);
		}
	}

	/**
	 * iterates the row data and creates a Row on the Sheet for each one
	 * @param s
	 * @param row
	 * @param lineData
	 * @param rowCnt
	 * @param header
	 */
	private void addRowData(Row row, Map<String, Object> lineData, Map<String, String> header) {
		//loop across the headerMap and find the matching value for each column
		int cellCnt = 0;
		for(String code : header.keySet()) {
			Cell c = row.createCell(cellCnt++);
			Object value = lineData.get(code);
			
			// Convert nulls to empty so they display properly in the spreadsheet
			if (value == null) value = "";
			CellValueType type = setCellValue(value, c);
			setBodyCellStyle(c, type);
		}
	}

	/**
	 * Here to allow sub-classes to override and apply individual styling to a cell versus blanket styling. Last two 
	 * parameters to be utilized for customized logic around applying styles in subclasses.
	 * @param cell XSSF cell object
	 * @param rowData - the rowData
	 * @param column - the column name
	 */
	protected void setBodyCellStyle(Cell cell, CellValueType type) {
		if(CellValueType.DATE.equals(type) || CellValueType.TIMESTAMP.equals(type)) {
			cell.setCellStyle(dateStyle);
		} else {
			cell.setCellStyle(bodyStyle); 
		}

	} 

	/**
	 * This method adds a cell to the top of the report with the title string info inside it
	 * @param s
	 */
	protected void addTitleCell(Sheet s,String title, int headerMapSize) {
		Row r = s.createRow(s.getPhysicalNumberOfRows());

		//fill it with the title string.
		Cell c = r.createCell(0);
		c.setCellValue(title);
		c.setCellStyle(this.titleStyle);

		//merge it the length of the report.
		s.addMergedRegion(new CellRangeAddress(0,0,0,headerMapSize-1));
	}

	/* (non-Javadoc)
	 * @see com.siliconmtn.data.report.SMTReportVO#setData(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setData(Object rows) {
		if (rows == null) this.rowData = new ArrayList<>();
		else if (rows instanceof Collection)
			this.rowData = (Collection<Map<String, Object>>) rows;
	}

	/**
	 * This method adds a cell to the top of the report with the title string info inside it
	 * @param s
	 */
	protected void addDateRow(Sheet s, int headerMapSize) {
		int numRows = s.getPhysicalNumberOfRows();
		Row r = s.createRow(numRows);

		//fill it with the title string.
		Cell c = r.createCell(0);
		c.setCellValue(DateFormat.toFormattedString(DatePattern.DATE_SLASH, new Date()));
		c.setCellStyle(this.bodyStyle);

		//merge it the length of the report.
		s.addMergedRegion(new CellRangeAddress(numRows,numRows,0,headerMapSize-1));
	}

	/**
	 * sets the boolean flag and sets the title string so a title cell can be triggered
	 * and added to the report before generation. 
	 * @param titleText Title of the document
	 */
	public void setTitle(String titleText) {
		this.titleText = titleText;
	}
	
	/**
	 * Returns the title of the Report
	 * @return
	 */
	public String getTitle() {
		return this.titleText;
	}

	/**
	 * static helper method reusable by all Excel reports - turns a POI Workbook
	 * into a byte[] to be streamed back to the client.
	 * This is public because it's static; 3rd party classes can use it to serialize custom-build Workbooks.
	 * @param wb
	 * @return
	 */
	public byte[] getBytes(Workbook wb) throws IOException {
		if (wb == null) return new byte[0];
		int estSize = 0;
		Iterator<Sheet> sIter = wb.sheetIterator();
		while(sIter.hasNext()) {
			Sheet s = sIter.next();
			estSize += s.getLastRowNum() * 5000; //figure 5kb for each row of data...a best guess.
		}

		//check against empty Sheets.
		estSize = Math.max(estSize, 100000);

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(estSize)) {
			wb.write(baos);
			return baos.toByteArray();
		}
	}

	/**
	 * Determines the cell type based upon the cell data and sets cell value appropriately
	 * @param value Value to store in the cell
	 * @param c XSSF cell object
	 */
	public CellValueType setCellValue(Object value, Cell c) {
		CellValueType cvt = CellValueType.STRING;
		if (value == null) value = "";

		// Determines the type of data nd csts it into the cell
		if (Pattern.matches("^[+-]?(?:\\d*\\.)?\\d+$", value.toString())) {
			Number num = NumberUtil.getNumber(value.toString());
			if (num instanceof Long) {
				c.setCellValue(num.longValue());
				cvt = CellValueType.INTEGER;
			} else {
				c.setCellValue(num.doubleValue());
				cvt = CellValueType.DOUBLE;
			}
		} else if (value instanceof Boolean) {
			c.setCellValue(BooleanUtil.toBoolean(value));
			cvt = CellValueType.BOOLEAN;
		} else if (value instanceof Timestamp) {
			c.setCellValue((Timestamp)value);
			cvt = CellValueType.TIMESTAMP;
		} else if (value instanceof Date) {
			c.setCellValue((Date)value);
			cvt = CellValueType.DATE;
		} else {
			if (Pattern.matches("^(\\d{4}-\\d{2}-\\d{2}).*", value + "")) {
				Date d = value.toString().length() > 10 ? 
						DateFormat.formatDate(DatePattern.ISO_SHORT, value + "") : 
						DateFormat.formatDate(DatePattern.DATE_DASH, value + "");
				
				c.setCellValue(d);
				cvt = CellValueType.DATE;
			} else {
				c.setCellValue(StringUtils.defaultString(value.toString()));
			}
		}
		
		return cvt;
	}
	
	/**
	 * Sets the max rows per sheet up to the maximum value for Excel.  If greater,
	 * max is reduced to the max allowable.  A minimum of 10 rows per sheet
	 * @param max
	 */
	public void setMaxRowsPerSheet(int max) {
		max = max < 10 ? 10 : max;
		maxRowsPerSheet = max > MAX_ROWS_PER_SHEET ? MAX_ROWS_PER_SHEET : max;
	}
	
	/**
	 * Retrieves the valueof the max rows per sheet
	 * @return
	 */
	public int getMaxRowsPerSheet() {
		return maxRowsPerSheet;
	}
	
	/**
	 * Returns the workbook
	 * @return
	 */
	public Workbook getWorkbook() {
		return wb;
	}
}