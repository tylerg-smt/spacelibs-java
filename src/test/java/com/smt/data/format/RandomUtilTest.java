package com.smt.data.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

/****************************************************************************
 * <b>Title</b>: RandomUtilTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description:</b> Test if random string is functioning as expected
 * and number generator is functioning as expected
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Bala Gayatri Bugatha
 * @version 1.0
 * @since Jan 29, 2021
 * @updates:
 ****************************************************************************/

public class RandomUtilTest {

	/**
	 * Test method for {@link com.smt.data.format.generateRandomNumber(int, int)}
	 */
	@Test
	public void generateRandomNumberTest() {
		assertTrue(2 <= RandomUtil.generateRandomNumber(2, 8) && RandomUtil.generateRandomNumber(2, 8) < 8 );
	}

	/**
	 * Test method for {@link com.smt.data.format.generateRandom(int) }
	 */
	@Test
	public void generateRandomTest() {
		assertEquals(true,RandomUtil.generateRandom(4).matches("[0-9a-z]{4}"));
	}
	/**
	 * Test method for {@link com.smt.data.format.generateExtendedRandom(int) }
	 */
	@Test
	public void generateExtendedRandomTest() {
		assertEquals(true,RandomUtil.generateExtendedRandom(4).matches("[0-9a-zA-Z!*$#]{4}"));
	}
	
	
}



