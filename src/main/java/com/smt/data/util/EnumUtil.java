package com.smt.data.util;

/********************************************************************
 * <b>Title: </b>EnumUtil.java<br/>
 * <b>Description: </b>Utility Class Housing Enum Utilities and time savers<br/>
 * <b>Copyright: </b>Copyright (c) 2017<br/>
 * <b>Company: </b>Silicon Mountain Technologies
 * @author James Camire
 * @version 3.x
 * @since Dec 13, 2017
 * Last Updated: 
 *******************************************************************/
public class EnumUtil {
	
	/**
	 * Can't access if all methods static
	 */
	private EnumUtil() {
		super();
	}

	/**
	 * Takes a String and converts it to an enum.  Must use the Enum CLass to make this work.  For example:
	 * EnumTest et = new EnumTest();
	 * SomeVal sv = SomeVal.val_two;
	 * SomeVal myVal = et.safeValueOf(SomeVal.class, "val_two");
	 * (SomeVal.equals(myVal)) // Evaluates to true
	 * @param enumType Enum class to pass.  For Example: MyEnum.class
	 * @param val String value to assign to the enum
	 * @return Typed Enum.  Null if val can't be converted.
	 */
	public static <E extends Enum<E>>  E safeValueOf(Class<E> enumType, String val) {
		return safeValueOf(enumType, val, null);
	}
	
	/**
	 * Takes a String and converts it to an enum.  Must use the Enum CLass to make this work.  For example:
	 * EnumTest et = new EnumTest();
	 * SomeVal sv = SomeVal.val_two;
	 * SomeVal myVal = et.safeValueOf(SomeVal.class, "val_two");
	 * (SomeVal.equals(myVal)) // Evaluates to true
	 * @param enumType Enum class to pass.  For Example: MyEnum.class
	 * @param val String value to assign to the enum
	 * @param dafaultVal Default value if no match
	 * @return Typed Enum.  Null if val can't be converted.
	 */
	public static <E extends Enum<E>>  E safeValueOf(Class<E> enumType, String val, E defaultVal) {
		E e = null;
		
		try {
			e = (E)Enum.valueOf(enumType, val);
		} catch (Exception ex) {
			if (defaultVal != null) e = defaultVal;
		}
		
		return e;
	}

}
