package com.smt.data.format;

// JDK 11
import java.io.Serializable;

// Apache commons 3.x
import org.apache.commons.lang3.StringUtils;

// Google Phone Libs 8.x
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.smt.data.text.StringUtil;

/****************************************************************************
 * <b>Title</b>: PhoneNumberFormat.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> Takes a phone number and formats it into the 
 * applicable notation.  Includes international formatting as well as US
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 13, 2021
 * @updates:
 ****************************************************************************/
public class PhoneNumberFormat implements Serializable {
	
	/**
	 * Enum to define the formatting type of the phone number
	 * @author etewa
	 *
	 */
	public enum FormatType {
		NATIONAL_FORMAT(1), 
		DOT_FORMATTING(2),
		DASH_FORMATTING(3),
		INTERNATIONAL_FORMAT(4);
		
		int type = 1;
		FormatType(int type) {
			this.type = type;
		}
	}
		
	/// Alias the formatting to keep code clean below
	private static final PhoneNumberUtil.PhoneNumberFormat INTL_FORMAT = PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;
	private static final PhoneNumberUtil.PhoneNumberFormat NTNAL_FORMAT = PhoneNumberUtil.PhoneNumberFormat.NATIONAL;
	
	/**
	 * This formatting uses +xx x xxx xxxx
	 */
	public static final int INTL_FORMATTING = 4;
	
	// Members
	private static final long serialVersionUID = 1l;
	private String formattedNumber = null;
	private String phoneNumber = null;
	private FormatType formatType = FormatType.NATIONAL_FORMAT;
	private String countryCode = "US";
	
	/**
	 * 
	 */
	public PhoneNumberFormat() {
		super();
	}
	
	/**
	 * Constructor that accepts a string phone number and phone type
	 * @param phoneNumber
	 * @param type
	 */
	public PhoneNumberFormat(String phoneNumber, FormatType type) {
		this();
		this.setPhoneNumber(phoneNumber);
		this.setFormatType(type);
	}
	
	/**
	 * 
	 * @param phoneNumber Phone number to be formatted
	 * @param countryCode ISO country code of the phone number
	 * @param type Formatting type
	 */
	public PhoneNumberFormat(String phoneNumber, String countryCode, FormatType type) {
		this(phoneNumber, type);
		this.setCountryCode(countryCode);
	}
	
	/**
	 * Return the phone number whith the country dialing code
	 * @return
	 */
	public String getFullNumber() {
		String countryDialingCode = PhoneNumberUtil.getInstance().getCountryCodeForRegion(countryCode) + "";
		if (phoneNumber.startsWith(countryDialingCode)) {
			return phoneNumber;
		} else {
			return countryDialingCode + phoneNumber;
		}
	}
	
	/**
	 * Formats the phone number based upon the type of formatting requested
	 * Automatically converts formatting to "INTERNATIONAL" or "NATIONAL" if the
	 * Address is a non-US address 
	 * @return
	 */
	public String getFormattedNumber() {
		if (StringUtils.isEmpty(formattedNumber) || formattedNumber.length() < 7) return formattedNumber;
		
		// If the request is for a dot or dash formatting for a non-US address
		// Convert the formatting type to International formatting
		StringBuilder sb = new StringBuilder();
		switch (formatType) {
			case DOT_FORMATTING:
				sb.append(assignUSFormatting('.', '.'));
				break;
			case DASH_FORMATTING:
				sb.append(assignUSFormatting('-', '-'));
				break;
			case INTERNATIONAL_FORMAT:
				sb.append(PhoneNumberUtil.getInstance().format(assignPhoneNumber(), INTL_FORMAT));
				break;
			default:
				sb.append(PhoneNumberUtil.getInstance().format(assignPhoneNumber(), NTNAL_FORMAT));
		}
		
		return sb.toString();
	}
	
	/**
	 * Dot or dash notation for US phones
	 * @param startDelim
	 * @return
	 */
	String assignUSFormatting(char startDelim, char endDelim) {
		if (StringUtils.isEmpty(formattedNumber)) return null; 
		StringBuilder sb = new StringBuilder();
		sb.append(formattedNumber.substring(0,3)).append(startDelim);
		sb.append(formattedNumber.substring(3,6)).append(endDelim);
		sb.append(formattedNumber.substring(6,formattedNumber.length()));
		
		return sb.toString();
	}
	
	/**
	 * Converts the string phone number to a java phonenumber
	 * @return
	 */
	PhoneNumber assignPhoneNumber() {
		PhoneNumber phone = null;
		PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
		try {
			phone = pnu.parse(formattedNumber, countryCode);
		} catch (Exception e) { /* Nothing to do */}
		
		return phone;
	}

	/**
	 * Gets the phone number
	 * @return
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		formattedNumber = StringUtil.removeNonNumeric(phoneNumber);
	}

	/**
	 * Sets the format type for the phone number
	 * @return
	 */
	public FormatType getFormatType() {
		return formatType;
	}

	/**
	 * Type of format
	 * @param formatType
	 */
	public void setFormatType(FormatType formatType) {
		this.formatType = formatType;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = StringUtils.defaultString(countryCode, "US");
	}

	/**
	 * returns an obfuscated version of the provided phone number.
	 * e.g.: (XXX)XXX-1234
	 * @param phoneNumber
	 * @return
	 */
	public static String obfuscatePhoneNumber(String phoneNumber) {
		if (StringUtils.isEmpty(phoneNumber)) return phoneNumber;
		int dashIdx = phoneNumber.indexOf('-');
		
		if (dashIdx == -1) dashIdx = phoneNumber.length()-5;
		if (dashIdx < 1) dashIdx = phoneNumber.length()/2;
		
		StringBuilder sb = new StringBuilder(phoneNumber.length());
		for (int x=0; x < phoneNumber.length(); x++ ) {
			char c = phoneNumber.charAt(x);
			if (x > dashIdx -1 || c =='(' || c == ')' || c == ' ') {
				sb.append(c);
			} else{
				sb.append('X');
			}
		}
		
		return sb.toString();
	}
	
}

