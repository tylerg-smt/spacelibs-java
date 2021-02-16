package com.siliconmtn.data.util;

// Junit 5
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

// Space Libs 1.x
import com.siliconmtn.data.format.DateFormat.DatePattern;

/****************************************************************************
 * <b>Title</b>: EnumUtilTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> Tests the enum utility class
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 28, 2021
 * @updates:
 ****************************************************************************/
class EnumUtilTest {

	/**
	 * Test method for {@link com.siliconmtn.data.util.EnumUtil#safeValueOf(java.lang.Class, java.lang.String)}.
	 */
	@Test
	void testSafeValueOfClassOfEString() {
		assertEquals(DatePattern.DATE_DASH, EnumUtil.safeValueOf(DatePattern.class, "DATE_DASH"));
		assertNull(EnumUtil.safeValueOf(DatePattern.class, null));
		assertNull(EnumUtil.safeValueOf(DatePattern.class, "skjdbkdhs"));
	}

	/**
	 * Test method for {@link com.siliconmtn.data.util.EnumUtil#safeValueOf(java.lang.Class, java.lang.String, java.lang.Enum)}.
	 */
	@Test
	void testSafeValueOfClassOfEStringE() {
		assertEquals(DatePattern.DATE_DASH, EnumUtil.safeValueOf(DatePattern.class, "DATE_DASH", DatePattern.DATE_FULL_MONTH));
		assertEquals(DatePattern.DATE_FULL_MONTH, EnumUtil.safeValueOf(DatePattern.class, null, DatePattern.DATE_FULL_MONTH));
		assertNull(EnumUtil.safeValueOf(DatePattern.class, null));
		assertEquals(DatePattern.DATE_FULL_MONTH, EnumUtil.safeValueOf(DatePattern.class, "HELLO", DatePattern.DATE_FULL_MONTH));
	}

}
