package com.smt.data.format;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/****************************************************************************
 * <b>Title</b>: NumberUtilTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 20, 2021
 * @updates:
 ****************************************************************************/
class NumberUtilTest {
	
	/**
	 * Tests converting a string into a number
	 * @throws Exception
	 */
	@Test
	public void testToIntString() throws Exception {
		assertEquals(0, NumberUtil.toInt(null));
		assertEquals(0, NumberUtil.toInt(""));
		assertEquals(0, NumberUtil.toInt("abc"));
		assertEquals(123, NumberUtil.toInt("123"));
		assertEquals(123, NumberUtil.toInt("123.45"));
		assertEquals(-123, NumberUtil.toInt("-123.45"));
		assertEquals(-123, NumberUtil.toInt("-123.45", 1));
	}

	/**
	 * Test the various patterns for converting a string to an int
	 * @throws Exception
	 */
	@Test
	public void testProcessInt() throws Exception {
		assertEquals(-123, NumberUtil.toInt("-123.45", 0, false));
		assertEquals(123, NumberUtil.toInt("+123.45", 0, false));
		assertEquals(123, NumberUtil.toInt("+123^&#.45", 0, true));
		assertEquals(10, NumberUtil.toInt("+123^&#.45", 10, false));
	}

	/**
	 * Test the various patterns for converting a string to a long
	 * @throws Exception
	 */
	@Test
	public void testToLongString() throws Exception {
		assertEquals(0, NumberUtil.toLong(null));
		assertEquals(0, NumberUtil.toLong(""));
		assertEquals(0, NumberUtil.toLong("abc"));
		assertEquals(-123, NumberUtil.toLong("-123.45"));
		assertEquals(-123, NumberUtil.toLong("-123.45", 0, false));
	}

	/**
	 * Tests the conversion of a string to a float
	 * @throws Exception
	 */
	@Test
	public void testToFloatString() throws Exception {
		assertEquals(0, NumberUtil.toFloat(null));
		assertEquals(0, NumberUtil.toFloat(""));
		assertEquals(6f, NumberUtil.toFloat("6f"));
		assertEquals(-6f, NumberUtil.toFloat("-6f"));
		assertEquals(6.543f, NumberUtil.toFloat("6.543"));
		assertEquals(-6.543f, NumberUtil.toFloat("-6.543"));
		assertEquals(0, NumberUtil.toFloat("6.54.-&^3", 0, false));
		assertEquals(6.543f, NumberUtil.toFloat("6.54.-&^3", 0, true));
		assertEquals(6.543123f, NumberUtil.toFloat("6.543.123"));
		assertEquals(6543f, NumberUtil.toFloat("6543."));
		assertEquals(-.6543123f, NumberUtil.toFloat("-.6543123"));
	}

	/**
	 * Tests the conversion of a string to a double
	 * @throws Exception
	 */
	@Test
	public void testToDoubleStringDoubleBoolean() throws Exception {
		assertEquals(0, NumberUtil.toDouble(null));
		assertEquals(0, NumberUtil.toDouble(""));
		assertEquals(6.0, NumberUtil.toDouble("6"));
		assertEquals(6.56, NumberUtil.toDouble("6.56"));
		assertEquals(-6.56, NumberUtil.toDouble("-6.56"));
		assertEquals(-6.56, NumberUtil.toDouble("-6.56", 0.0, false));
		assertEquals(0, NumberUtil.toDouble("-6.54.-&^3", 0.0, false));
		assertEquals(6.0, NumberUtil.toDouble("6.", 0.0, false));
		assertEquals(6.0, NumberUtil.toDouble("6.", 0.0, true));
		assertEquals(-6.0, NumberUtil.toDouble("-6.", 0.0, true));
	}


}
