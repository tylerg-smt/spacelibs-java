package com.smt.data.report;

// JDK 1.8
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

// Apache commons 3.x
import org.apache.commons.lang3.StringUtils;

// Apache POI 3.13
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

// SpaceLibs 1.x
import com.smt.data.format.BooleanUtil;
import com.smt.data.format.DateFormat;
import com.smt.data.format.DateFormat.DatePattern;
import com.smt.data.format.NumberUtil;
import com.smt.data.report.ExcelStyleFactory.Styles;

// Lombok 1.18.x
import lombok.extern.log4j.Log4j2;

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
@Log4j2
public class ExcelReport extends AbstractReport {
	private static final long serialVersionUID = 1l;
	private static final int MAX_ROWS_PER_SHEET = 64000;
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
	 * @param headerMap
	 * @param s
	 */
	public ExcelReport(Map<String, String> headerMap, ExcelStyleInterface style) {
		super();
		wb = new HSSFWorkbook();
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
	public byte[] generateReport() {
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
			if (count > MAX_ROWS_PER_SHEET) {
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
		log.info("Data: " + expandColumnFlag + "|" + cost);
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
			setCellValue(value, c);
			boolean isDate = (value instanceof Date || value instanceof Timestamp || DateFormat.isDate(value));
			setBodyCellStyle(c, lineData, code, isDate);
		}
	}

	/**
	 * Here to allow sub-classes to override and apply individual styling to a cell versus blanket styling. Last two 
	 * parameters to be utilized for customized logic around applying styles in subclasses.
	 * @param Cell
	 * @param rowData - the rowData
	 * @param column - the column name
	 */
	protected void setBodyCellStyle(Cell cell, Map<String, Object> rowData, String column, boolean isDate) {
		if(isDate) {
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
		if (rows == null) rows = new ArrayList<>();
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
	 * @param contents
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
	public byte[] getBytes(Workbook wb) {
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
		} catch (IOException ioe) {
			log.error("could not write output stream", ioe);
		} finally {
			try {
				wb.close();
			} catch (Exception e) {
				log.error("could not close workbook", e );
			}
		}
		return new byte[0];
	}

	/**
	 * Determines the cell type based upon the cell data and sets cell value appropriately
	 * @param value
	 * @return
	 */
	public void setCellValue(Object value, Cell c) {

		if (value instanceof Number || "0".equals(value) ) {
			c.setCellValue(NumberUtil.toDouble(value+"",0));
		} else if (value instanceof Boolean) {
			c.setCellValue(BooleanUtil.toBoolean(value));
		} else {
			c.setCellValue(StringUtils.defaultString(value + ""));
		}
	}
}