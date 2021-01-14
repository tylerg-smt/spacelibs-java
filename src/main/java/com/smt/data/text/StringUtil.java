package com.smt.data.text;

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
		if (data == null || data.length() < 1) return null;

		StringBuilder newVal = new StringBuilder();
		for (char a : data.toCharArray()) {
			if (Character.isDigit(a) || (a == '.')) newVal.append(a);
		}
		
		return newVal.toString();
	}
}
