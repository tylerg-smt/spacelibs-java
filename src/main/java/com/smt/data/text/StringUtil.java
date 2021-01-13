package com.smt.data.text;

// Apache Commons 3.1.1
import org.apache.commons.lang3.StringUtils;

/****************************************************************************
 * <b>Title</b>: StringUtil.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> Extends the apache commons Strign Utils class wtih some additional
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
		if (StringUtils.isEmpty(str) ||  StringUtils.isEmpty(searchStr)) return new int[0];
		int matches = StringUtils.countMatches(str, searchStr);
		int[] items = new int[matches];
		int loc = -1;
		
		for (int i=0; i < 50; i++) {
			
			loc = str.toString().indexOf(searchStr.toString(), loc + 1);
			if (loc < 0) break;
			items[i] = loc;
		}

		return items;
	}

}
