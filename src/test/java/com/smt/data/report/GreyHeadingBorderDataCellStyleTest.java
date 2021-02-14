package com.smt.data.report;

// Junit 5
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/****************************************************************************
 * <b>Title</b>: GreyHeadingBorderDataCellStyleTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> Tests the grey border heading excel report style class
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 1.0
 * @since Feb 14, 2021
 * @updates:
 ****************************************************************************/
class GreyHeadingBorderDataCellStyleTest {

	/**
	 * Test method for {@link com.smt.data.report.GreyHeadingBorderDataCellStyle#buildStyleMap()}.
	 */
	@Test
	void testBuildStyleMap() {
		GreyHeadingBorderDataCellStyle ncs = new GreyHeadingBorderDataCellStyle();
		ncs.buildStyleMap();
		assertEquals(3, ncs.getStyleMap().size());
	}

	/**
	 * Test method for {@link com.smt.data.report.GreyHeadingBorderDataCellStyle#GreyHeadingBorderDataCellStyle()}.
	 */
	@Test
	void testNoStyleCellStyle() {
		GreyHeadingBorderDataCellStyle ncs = new GreyHeadingBorderDataCellStyle();
		assertNotNull(ncs);
	}

}
