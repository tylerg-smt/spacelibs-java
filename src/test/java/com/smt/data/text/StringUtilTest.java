package com.smt.data.text;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


/****************************************************************************
 * <b>Title</b>: StringUtilTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> CHANGE ME!!
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
		Integer[] indexes = StringUtil.everyIndexOf("james camire", "a");
		assertEquals(indexes.length, 2);
		assertEquals(indexes[0], 1);
		assertEquals(indexes[1], 7);
		
		indexes = StringUtil.everyIndexOf("the time for all of the good things", "the");
		assertEquals(indexes.length, 2);
		assertEquals(indexes[0], 0);
		assertEquals(indexes[1], 20);
		
		indexes = StringUtil.everyIndexOf("the time for all of the good things", "");
		assertEquals(indexes.length, 0);
	}

}
