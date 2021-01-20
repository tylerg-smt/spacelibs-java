package com.smt.data.format;

import com.smt.data.text.StringUtil;

/****************************************************************************
 * <b>Title</b>: NumberUtil.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> Number utilities utilized to convert strings into various
 * number types.  Works with mixed values and strings in conversion
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 20, 2021
 * @updates:
 ****************************************************************************/
public class NumberUtil {

	/**
	 * Not implemented as all methods are designed to be static
	 */
	private NumberUtil() {
		super();
	}
	
	/**
	 * Converts a String into an int
	 * Return an Integer with a value of 0 if the conversion fails
	 * 
	 * @param val String to be converted
	 * @return Integer of value 0 if conversion fails
	 */
	public static int toInt(String val) {
		return toInt(val, 0, true);
	}
	
	/**
	 * Converts a String to an int. If data is not a number , use the
	 * default val.  this method acts as the controller.
	 * 
	 * @param val String to be converted
	 * @param defaultVal Default value for non String or non value in string
	 * @return Integer, default val if failed
	 */
	public static int toInt(String val, int defaultVal, boolean removeNonAlphaNum) {
		if (val == null || val.isEmpty()) return defaultVal;
		
		//for long and int remove data after the "point"
		val = makeWholeNumber(val.trim());
		
		return processInt(val, checkForNegativeValue(val), defaultVal, removeNonAlphaNum);
	}
	
	/**
	 * processes the conversion from string to Integer
	 * @param val value to convert
	 * @param defaultVal If empty string or error reformatting, return default value
	 * @param negative is value negative
	 * @param removeNonAlphaNum This removes all non-alpha characters and then converts
	 * @return
	 */
	private static int processInt(String val, boolean negative, int defaultVal, boolean removeNonAlphaNum) {
		int convertedString = 0;
		try {
			// Only remove alpha characters if requested.  use the negitiveStringFlag to replace any missing signs
			if (removeNonAlphaNum) {
				convertedString = Integer.parseInt(signedValue(StringUtil.removeNonAlphaNumeric(val), negative));

			}else {
				// else just attempt to convert as is.
				convertedString = Integer.parseInt(val);

			}
		} catch (Exception nfe) {
			convertedString = defaultVal;
		}
		
		return convertedString;
	}

	/**
	 * Converts a String into an Long
	 * 
	 * @param val String to be converted
	 * @return Long of value 0 if conversion fails
	 */
	public static long toLong(String val) {
		return toLong(val, 0);
	}
	
	/**
	 * Converts a String into an Long
	 * 
	 * @param val String to be converted
	 * @return Long of value 0 if conversion fails
	 */
	public static long toLong(String val, long defaultValue) {
		return toLong(val, defaultValue, true);
	}

	/**
	 * Converts a String into a long, acts as the controller for the process
	 * 
	 * @param val
	 * @param defaultValue
	 * @param removeNonAlphaNum
	 * @return
	 */
	public static long toLong(String val, long defaultValue, boolean removeNonAlphaNum) {
		// Convert a String into an long
		// Return a Long of the default value if the conversion fails
		if (val == null || val.isEmpty()) return defaultValue;
		
		//for long and int remove data after the "point"
		val = makeWholeNumber(val.trim());

		//return a processed long value
		return processLong(val,checkForNegativeValue(val),removeNonAlphaNum ,defaultValue  );

	}
	
	/**
	 * process the conversion from String to Long
	 * @param val Value to process
	 * @param negative is the number negative
	 * @param removeNonAlphaNum removes all non-numbers in the val
	 * @param defaultValue if empty/null/error converting, return this value
	 * @return long value
	 */
	private static long processLong(String val,boolean negative, boolean removeNonAlphaNum, long defaultValue) {

		long convertedString = 0;

		try {	
			// Only remove alpha characters if requested.  replace the negative sign that was removed by remove non alpha
			if (removeNonAlphaNum) {
				convertedString = Long.parseLong(signedValue(StringUtil.removeNonAlphaNumeric(val), negative));
			}else {
				//else just try and parse the string as is
				convertedString = Long.parseLong(val.toString());
			}

		} catch (Exception nfe) {
			return defaultValue;
		}
		return convertedString;
	}
	
	/**
	 * Converts a String into an Float
	 * 
	 * @param val String to be converted
	 * @return Float value 0.0 if conversion fails
	 */
	public static float toFloat(String val) {
		return toFloat(val, 0);
	}
	
	/**
	 * Converts a String into an Float
	 * @param val
	 * @param i
	 * @return
	 */
	public static float toFloat(String val, float defaultValue) {
		return toFloat(val,defaultValue, true);
	}
	
	/**
	 * converts a string into a Float, acts as the controller for the process
	 * @param val
	 * @param defaultValue
	 * @param removeNonAlphaNum
	 * @return
	 */
	public static float toFloat(String val, float defaultValue, boolean removeNonAlphaNum) {
		// Convert a String into an float.
		// Return a Float of the default value if the conversion fails
		if (val == null || val.isEmpty()) return defaultValue;

		//find the index of the first point in the possible number
		int indexOfDecimalPoint = val.indexOf(".");

		//return processed number
		return processFloat(val.trim(),checkForNegativeValue(val), indexOfDecimalPoint, removeNonAlphaNum, defaultValue);		
	}
	
	/**
	 * processes the conversion from String to Float
	 * @param value
	 * @param negative
	 * @param indexOfDecimalPoint
	 * @param removeNonAlphaNum
	 * @param defaultValue
	 * @return
	 */
	private static float processFloat(String value, boolean negative, int indexOfDecimalPoint, boolean removeNonAlphaNum, float defaultValue) {
		float convertedString = 0f;

		try {
			// Only remove alpha characters if requested replace sign and point
			if (removeNonAlphaNum) {
				int index = value.indexOf(".");

				// if there is a decimal, split on the first decimal, remove alphas and put back together
				if (index > -1) {
					String major = "";
					String minor = "";
					major = StringUtil.removeNonAlphaNumeric(value.substring(0, index));
					if (index + 1 < value.length()) minor = StringUtil.removeNonAlphaNumeric(value.substring(index + 1));

					value = (negative ? "-" : "") + major + "." + minor;
				} else {
					// If no decimals, process the request normally
					String val = StringUtil.removeNonAlphaNumeric(value);
					value = signedValue(val, negative);
				}
			}

			convertedString = Float.parseFloat(value);
			
		} catch (Exception nfe) {
			return defaultValue;
		}
		
		return convertedString;
	}
	
	/**
	 * Converts a String into a Double
	 * 
	 * @param val String to be converted
	 * @return Double of value 0.0 if conversion fails
	 */
	public static double toDouble(String val) {
		return toDouble(val , 0.0);
	}

	/**
	 * Converts a String into a double with default double value.
	 * @param parameter
	 * @param d
	 * @return
	 */
	public static double toDouble(String val, double d) {
		return toDouble(val, d, true);
	}
	
	/**
	 * Converts a String into a Double, acts as the controller for the process
	 * @param val
	 * @param defaultValue
	 * @param removeNonAlphaNum
	 * @return
	 */
	public static double toDouble(String val, double defaultValue, boolean removeNonAlphaNum) {
		// Convert a String into an Double object.
		// Return a Double of the default value if the conversion fails
		if (val == null || val.isEmpty()) return defaultValue;

		//return processed value
		return processDouble(val.trim(), checkForNegativeValue(val), removeNonAlphaNum, defaultValue, val.indexOf("."));
	}

	/**
	 * process the conversion from String to Double
	 * @param value
	 * @param negative
	 * @param removeNonAlphaNum
	 * @param defaultValue
	 * @param indexOfDecimalPoint
	 * @return
	 */
	private static double processDouble(String value, boolean negative, boolean removeNonAlphaNum, Double defaultValue,int indexOfDecimalPoint) {

		double convertedString = 0.0;

		try {
			// Only remove alpha characters if requested replace sign and point
			if (removeNonAlphaNum) {
				int index = value.indexOf(".");

				// if there is a decimal, split on the first decimal, remove alphas and put back together
				if (index > -1) {
					String major = "", minor = "";
					major = StringUtil.removeNonAlphaNumeric(value.substring(0, index));
					if (index + 1 < value.length()) minor = StringUtil.removeNonAlphaNumeric(value.substring(index + 1));

					value = (negative ? "-" : "") + major + "." + minor;
				} else {
					value = signedValue(StringUtil.removeNonAlphaNumeric(value), negative);
				}
			}

			convertedString = Double.parseDouble(value.toString());

		} catch (Exception nfe) {
			return defaultValue;
		}
		
		return convertedString;
	}
	
	/**
	 * This method will re-apply he negative flag so it is not complete missed when we remove alpha
	 * @param val String value to be checked
	 * @param negativeStringFlag is the number negative?
	 * @return
	 */
	private static String signedValue(String val, boolean isNegative) {
		StringBuilder sb = new StringBuilder((val.length() +1));
		sb.append(val);
		
		//replaces the dash if it used to be present.
		if (isNegative) sb.insert(0, "-");
		
		return sb.toString();
	}
	
	
	/**
	 * This method removes the point and any digits to the right
	 * @param val value to strip
	 * @return stripped value with no decimal place
	 */
	private static String makeWholeNumber(String val) {
		return val.indexOf(".") != -1 ? val.substring(0, val.indexOf(".")) : val;
	}
	
	/**
	 * this method checks the incoming string for a first char of -
	 * returns a boolean indicating the string contains a negative number
	 * @param val String value to check
	 * @return true if number is negative
	 */
	private static boolean checkForNegativeValue(String val) {
		return "-".equals(val.substring(0, 1));
	}
}
