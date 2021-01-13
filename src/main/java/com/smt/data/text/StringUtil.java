package com.smt.data.text;

// JDK 11.x
import java.util.ArrayList;
import java.util.List;

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
	public static Integer[] everyIndexOf(CharSequence str, CharSequence searchStr) {
		if (StringUtils.isEmpty(str) ||  StringUtils.isEmpty(searchStr)) return new Integer[0];
		
		List<Integer> items = new ArrayList<>();
		int loc = -1;
		
		for (int i=0; i < 50; i++) {
			
			loc = str.toString().indexOf(searchStr.toString(), loc + 1);
			if (loc < 0) break;
			items.add(loc);
		}

		return items.toArray(new Integer[0]);
	}

}
