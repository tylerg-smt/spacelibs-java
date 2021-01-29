package com.smt.data.format;

import java.util.Random;

/****************************************************************************
 * <b>Title</b>RandomUtil.java
 * <p/>
 * <b>Description: Generate random string from generic character & extended character set </b>
 * <p/>
 * <b>Copyright:</b> Copyright (c) 2021
 * <p/>
 * <b>Company:</b> Silicon Mountain Technologies
 * <p/>
 * 
 * @author Bala Gayatri Bugatha
 * @version 1.0
 * @since Jan 29, 2021
 ****************************************************************************/
public class RandomUtil {
	
	//Character set defined and declared
	private final static String lowerCase="abcdefghijklmnopqrstuvwxyz";
	private final static String upperCase="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final static String number="0123456789";
	private final static String symbol="!*$#";
	
	/**
	 * Generates a random string of desired length with string "abcdefghijklmnopqrstuvwxyz1234567890"
	 * @param len of string
	 * @return Generated random string
	 */
	public static String generateRandom(int len) {
		return generateAnyRandom(len, lowerCase+number);
	}
	
	/**
	 * Generates a random string of desired length with string "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!*$#"
	 * @param len of string
	 * @return Generated random string
	 */
	public static String generateExtendedRandom(int len) {
		return generateAnyRandom(len, lowerCase+upperCase+number+symbol);
	}
	/**
	 * Helper method for generateValidRandom(int) and generateExtendedRandom(int)
	 * @param len int and str concatenated string 
	 * @return Generated random string
	 */
	private static String generateAnyRandom(int len, String str) {

		Random random = new Random();
		String result = "";
		for (int i = 0; i < len; i++)
			result += str.charAt(random.nextInt(str.length()));
		return result;
	}

	/**
	 * Generates a random number
	 * @param Integer value indicating lowLimit inclusive and Integer value
	 *                indicating highLimitExclusive
	 * @return Generated Random number in set limits
	 */
	public static int generateRandomNumber(int lowLimitInclusive, int highLimitExclusive) {
		Random random = new Random();
		return random.ints(lowLimitInclusive, highLimitExclusive).findFirst().getAsInt();
	}
}
