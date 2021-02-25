package com.siliconmtn.data.text;

// JDK 11.x
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.UUID;

// Apache Commons 3.1.1
import org.apache.commons.lang3.StringUtils;

/****************************************************************************
 * <b>Title</b>: StringUtil.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> Extends the apache commons String Utils class with some additional
 * capabilities
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 12, 2021
 * @updates:
 ****************************************************************************/
public class StringUtil extends StringUtils {

	/**
	 * 
	 */
	private StringUtil() {
		super();
	}
	
	/**
	 * Returns an array of the index for each of the matching 
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public static int[] everyIndexOf(CharSequence str, CharSequence searchStr) {
		int[] items;
		if (isEmpty(str) || isEmpty(searchStr)) return new int[0];
		
		items = new int[countMatches(str, searchStr)];
		int loc = -1;
		
		for (int i=0; i < items.length; i++) {
			
			loc = str.toString().indexOf(searchStr.toString(), loc + 1);
			items[i] = loc;
		}

		return items;
	}
	
	/**
	 * Deletes all of the non-alpha (Aa-Zz) and NoN-Numeric (0-9) characters in the data
	 * @param data 
	 * @return parsed data.
	 */
	public static String removeNonAlphaNumeric(String data) {
		return removeNonAlphaNumeric(data, true);
	}

	/**
	 * Deletes all of the non-alpha (Aa-Zz) and NoN-Numeric (0-9) characters in the data
	 * @param data String to remove characters form
	 * @param removeSpace Provides an option to remove or leave spaces.  Default is to remove
	 * @return parsed data.
	 */
	public static String removeNonAlphaNumeric(String data, boolean removeSpace) {
		if (data == null || data.length() < 1) return data;

		StringBuilder newVal = new StringBuilder();
		for (char a : data.toCharArray()) {
			if (Character.isWhitespace(a) || Character.isDigit(a) || Character.isLetter(a)) {
				if (Character.isWhitespace(a) && removeSpace) continue;
				newVal.append(a);
			}
		}
		
		return newVal.toString();
	}
	
	/**
	 * returns an obfuscated version of the provided email address.
	 * e.g.: j*****n@siliconmtn.com
	 * @param email
	 * @return
	 */
	public static String obfuscateEmail(String email) {
		if (isEmpty(email)) return email;

		StringBuilder sb = new StringBuilder(email.length());
		for (int x=0; x < email.length(); x++ ) {
			sb.append(x > 0 && x < email.indexOf('@') ? '*' : email.charAt(x));
		}
		return sb.toString();
	}
	
	/**
	 * Deletes all of the non-numeric (0-9) characters in the data
	 * @param data 
	 * @return parsed data.
	 */
	public static String removeNonNumeric(String data) {
		if (StringUtils.isEmpty(data)) return null;

		StringBuilder newVal = new StringBuilder();
		for (char a : data.toCharArray()) {
			if (Character.isDigit(a) || (a == '.')) newVal.append(a);
		}
		
		return newVal.toString();
	}
	
	/**
	 * Uses reflection to find all fields in the class and 
	 * output the data in the following format:<br>
	 * fieldName:value|fieldName:value....|fieldName:value
	 * @param o Object to get values for.
	 * @return formatted string of variables
	 */
	public static String getToString(Object o) {
		return getToString(o, ":");
	}
	
	/**
	 * Uses reflection to find all fields in the class and 
	 * output the data in the following format:<br>
	 * fieldName:value|fieldName:value....|fieldName:value
	 * @param o Object to get values for.
	 * @param sep separator between values
	 * @return formatted string of variables
	 */
	public static String getToString(Object o, String sep) {
		if (o == null) return "";
		
		if (o.getClass().isArray()) {
			return getToString((Object[])o, sep);
		}
		
		Class<?> c = o.getClass();
		Method[] methods = c.getMethods();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < methods.length; i++) {
			String name = methods[i].getName();
			if (methods[i].getParameterTypes().length > 0 || methods[i].getModifiers() != Modifier.PUBLIC) continue;

			try {
				if ((name.startsWith("get") || name.startsWith("is"))) {
					sb.append(name).append(":");
					sb.append(methods[i].invoke(o)).append(sep);
				}
			} catch (Exception e) { /* Nothing to do here */ }
			
		}

		return sb.toString();
	}
	
	/**
	 * Formats an array into a delimited String
	 * @param arr Array to iterate.  
	 * @param delim Character to use as the delimiter
	 * @return Delimited String. "" if array is empty
	 */
	private static String getToString(Object[] arr, String delim) {
		if (arr.length == 0) return ""; 
		
		StringBuilder sb = new StringBuilder("[");
		
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) sb.append(delim);
			sb.append(arr[i] == null ? "" : arr[i]);
		}

		sb.append("]");
		return sb.toString();
	}
	
	
	/**
	 * Check if a given string is a valid UUID and return it as a UUID or null
	 * @param potentialUUID the string to test
	 * @return the UUID if valid, else null
	 */
	public static UUID getUUID(String potentialUUID) {
		if (potentialUUID == null || potentialUUID.length() != 36)
			return null;		
		
		try {
			return UUID.fromString(potentialUUID);
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Pads the supplied character to fill in the empty spaces.  For example, 
	 * to create a string with a length of 5, and fill blanks with 0 ... to the 
	 * left or right<br/>
	 * <p>padLeft("d", "0", 5); </p>
	 * <p>This will return "0000d"</p>
	 * <p> If the length of the src String is greater than the length, the
	 * returned string will be truncated</p>
	 * @param src Source String to pad
	 * @param fill Character to use to pad the string
	 * @param length final length of the string.  All characters less than the length 
	 * will be padded with the fill character
	 * @return
	 */
	public static String padLeft(String src, char fill, int length) {
		return  pad(src, fill, length, false);
	}
	
	/**
	 * Pads the supplied character to fill in the empty spaces.  For example, 
	 * to create a string with a length of 5, and fill blanks with 0 ...<br/>
	 * This is a function that performs either padding
	 * @param src Source String to pad
	 * @param fill Character to use to pad the string
	 * @param length final length of the string.  All characters less than the length 
	 * will be padded with the fill character
	 * @param isPadRight Boolean to determine if the padding is left or right
	 * @return
	 */
	private static String pad(String src, char fill, int length, boolean isPadRight) {
		if (src == null) src = ""; 
		StringBuilder sb = new StringBuilder();
		int startLoc = length - src.length();

		int stringLen = src.length();
		if (stringLen > length) stringLen = length;
		if (isPadRight) sb.append(src.substring(0, stringLen));
		for (int i = 0; i < startLoc; i++) {
			sb.append(fill);
		}
		
		if (! isPadRight)sb.append(src.substring(0, stringLen));

		return sb.toString();
	}

	/**
	 * Pads the supplied character to fill in the empty spaces.  For example, 
	 * to create a string with a length of 5, and fill blanks with 0 ...<br/>
	 * <p>padLeft("d", "0", 5); </p>
	 * <p>This will return "d0000"</p>
	 * <p> If the length of the src String is greater than the length, the
	 * returned string will be truncated</p>
	 * @param src
	 * @param fill
	 * @param length
	 * @return
	 */
	public static String padRight(String src, char fill, int length) {
		return pad(src, fill, length, true);
	}
}
