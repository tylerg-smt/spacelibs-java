package com.smt.data.report;

// Junit 5
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// JDK 11.x
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Space Libs 1.x
import com.smt.data.format.DateFormat;
import com.smt.data.format.DateFormat.DatePattern;
import com.smt.data.report.ExcelStyleFactory.Styles;

/****************************************************************************
 * <b>Title</b>: ExcelReportTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b>Test class for the Excel report egneration class
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Feb 14, 2021
 * @updates:
 ****************************************************************************/
class ExcelReportTest {
	
	private ExcelReport rpt;
	private Map<String, String> headerMap;
	private List<Map<String, Object>> dataSet;
	
	@BeforeEach
	void setUpBeforeEach() throws Exception {
		headerMap = new HashMap<>();
		headerMap.put("ID", "Identification");
		headerMap.put("NAME", "Name");
		headerMap.put("AGE", "Age");
		headerMap.put("DOB", "Date of Birth");
		rpt = new ExcelReport(headerMap);
		
		// Assign the data
		dataSet = new ArrayList<>();
		Map<String, Object> dataMap1 = new HashMap<>();
		dataMap1.put("ID", "12345");
		dataMap1.put("NAME", "James Camire");
		dataMap1.put("AGE", 56);
		dataMap1.put("DOB", DateFormat.formatDate(DatePattern.DATE_DASH, "1964-08-25"));
		
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("ID", "12345");
		dataMap.put("NAME", "James Camire");
		dataMap.put("AGE", 56);
		dataMap.put("DOB", DateFormat.formatDate(DatePattern.DATE_DASH, "1964-08-25"));
		
		dataSet.add(dataMap);
		dataSet.add(dataMap1);
		rpt.setData(dataSet);
	}

	/**
	 * Test method for {@link com.smt.data.report.ExcelReport#generateReport()}.
	 * @throws IOException 
	 */
	@Test
	void testGenerateReport() throws IOException {
		byte[] data = rpt.generateReport();
		assertNotNull(data);
		assertEquals(4608, data.length);
		Files.write(new File("/home/etewa/Desktop/test.xlsx").toPath(), data);
	}

	/**
	 * Test method for {@link com.smt.data.report.ExcelReport#setData(java.lang.Object)}.
	 */
	@Test
	void testSetData() {
		rpt.setData(null);
		rpt.setData("Test");
		rpt.setData(dataSet);
	}
	
	/**
	 * Test method for {@link com.smt.data.report.ExcelReport#setFileName(java.lang.String)}.
	 */
	@Test
	void testSetFilename() {
		rpt.setFileName("MyReport.xlsx");
		assertEquals("MyReport.xlsx", rpt.getFileName());
		assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", rpt.getContentType());
	}

	/**
	 * Test method for {@link com.smt.data.report.ExcelReport#ExcelReport(java.util.Map, com.smt.data.report.ExcelStyleFactory.Styles)}.
	 */
	@Test
	void testExcelReportMapOfStringStringStyles() {
		rpt = new ExcelReport(headerMap, Styles.BORDERS_ONLY);
		assertNotNull(rpt);
	}

	/**
	 * Test method for {@link com.smt.data.report.ExcelReport#setTitle(java.lang.String)}.
	 */
	@Test
	void testSetTitle() {
		rpt.setTitle("My Report Title");
		assertEquals("My Report Title", rpt.getTitle());
	}

	/**
	 * Test method for {@link com.smt.data.report.ExcelReport#getBytes(org.apache.poi.ss.usermodel.Workbook)}.
	 */
	@Test
	void testGetBytes() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.report.ExcelReport#setCellValue(java.lang.Object, org.apache.poi.ss.usermodel.Cell)}.
	 */
	@Test
	void testSetCellValue() {
		fail("Not yet implemented");
	}

}
