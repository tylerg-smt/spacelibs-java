package com.smt.data.text;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


/****************************************************************************
 * <b>Title</b>: StringUtilTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 13, 2021
 * @updates:
 ****************************************************************************/
class StringUtilTest {

	/**
	 * Test method for {@link com.smt.data.text.StringUtil#everyIndexOf(java.lang.CharSequence, java.lang.CharSequence)}.
	 */
	@Test
	void testEveryIndexOf() {
		int[] indexes = StringUtil.everyIndexOf("james camire", "a");
		assertEquals(2, indexes.length);
		assertEquals(1, indexes[0]);
		assertEquals(7, indexes[1]);
		
		indexes = StringUtil.everyIndexOf("the time for all of the good things", "the");
		assertEquals(2, indexes.length);
		assertEquals(0, indexes[0]);
		assertEquals(20, indexes[1]);
		
		indexes = StringUtil.everyIndexOf("the time for all of the good things", "");
		assertEquals(0, indexes.length);
	}

}
