package com.siliconmtn.data.report;

//app libs
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/****************************************************************************
 * <b>Title</b>: ExcelStyleInterface.java <p/>
 * <b>Project</b>: WebCrescendo <p/>
 * <b>Description: </b> Style interface to be inherited by concrete styles
 * <p/>
 * <b>Copyright:</b> Copyright (c) 2016<p/>
 * <b>Company:</b> Silicon Mountain Technologies<p/>
 * @author Ryan Riker
 * @version 2.0
 * @since Sep 9, 2016<p/>
 * @updates:
 ****************************************************************************/
public interface ExcelStyleInterface {

	/**
	 * Returns the styles for the excel header row
	 * @return
	 */
	public CellStyle getHeadingStyle(Workbook wb);
	
	/**
	 * Returns the styles for the excel title
	 * @return
	 */
	public CellStyle getTitleStyle(Workbook wb);
	
	/**
	 * Returns the styles for the body title
	 * @return
	 */
	public CellStyle getBodyStyle(Workbook wb);
	
	/**
	 * Defines whether the columns should be expanded by default
	 * @return
	 */
	public boolean getExpandColumnFlag();
	
	/**
	 * Sets whether the columns should be expanded by default
	 * @return
	 */
	public void setExpandColumnFlag(boolean expandColumnFlag);
	
	/**
	 * Defines whether the date should be displayed under the title row 
	 * @return
	 */
	public boolean displayDate();
	
	/**
	 * Sets the flag to display the date on the report header
	 * @param displayDate
	 */
	public void setDisplayDate(boolean displayDate);

	/**
	 * @param wb
	 * @return
	 */
	public CellStyle getDateStyle(Workbook wb);

}
