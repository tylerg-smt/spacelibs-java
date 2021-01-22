package com.smt.data.format;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.apache.commons.lang3.time.FastDateFormat;

public class DateFormat {
	
	// Define and Declare multiple date patterns for string to date formatting
	public enum DatePatterns {
		
		//date & time formats
		DATE_TIME_SLASH_PATTERN("MM/dd/yyyy HH:mm:ss"),
		DATE_TIME_DASH_PATTERN("yyyy-MM-dd HH:mm:ss"),
		DATETIME_SLASH_PATTERN("MM/dd/yyyy HH:mm"),
		DATETIME_SLASH_SHORT_PATTERN("MM/dd/yy HH:mm"),
		DATETIME_DASH_PATTERN("yyyy-MM-dd HH:mm"),
		ISO_PATTERN("yyyy-MM-dd'T'HH:mm:ss.SSS zzz"),
		ISO_PATTERN_SHORT("yyyy-MM-dd'T'HH:mm:ss.SSS"),

		//date formats
		DATE_DASH_PATTERN("yyyy-MM-dd"),
		DATE_DASH_SIMPLE_YEAR_PATTERN("MM-dd-yyyy"),
		DATE_SLASH_PATTERN("MM/dd/yyyy"),
		DATE_SLASH_ABBREV_PATTERN("MMM/dd/yyyy"),
	    DATE_SLASH_SHORT_PATTERN("MM/dd/yy"),
		DATE_LONG("EEEE MMMM dd, yyyy"),
		DATE_FULL_MONTH("MMMMMMMMM dd, yyyy"),
		DATE_SHORT_MONTH("MMM dd, yyyy"),
	    DATE_NOSPACE_PATTERN("yyyyMMdd"),
		DATE_SHORT_NOSPACE_PATTERN("yyMMdd"),
		DATE_SIMPLE_PATTERN("MMddyy"),
		DATE_SIMPLE_YEAR_PATTERN("MMddyyyy"),
		DATE_SHORT_PATTERN("MMyy"),
		DATE_SLASH_MONTH_PATTERN("MM/yyyy"),
		DATE_SLASH_MONTH_SHORT_PATTERN("MM/yy"),
		DATE_LONG_DAY_OF_WEEK_PATTERN("EEE MMM dd HH:mm:ss zzz yyyy"),
		
		//Date Time formats that are RSS Related.
		RFC_2822_NO_SEC("EEE dd MMM yyyy HH:mm zzz"),
		RFC_2822("EEE dd MMM yyyy HH:mm:ss zzz"),
		
		//Date Time formats that are ATOM Feed Related
		ISO_8601_NO_SEC("yyyy-MM-dd'T'HH:mmZ"),
		ISO_8601("yyyy-MM-dd'T'HH:mm:ssZ"),

		DATE_TIME_NOSPACE_PATTERN("yyyyMMdd_HHmmss"),
		DATE_TIME_DASH_PATTERN_12HR("yyyy-MM-dd hh:mm a"),
		DATE_TIME_SLASH_PATTERN_12HR("MM/dd/yyyy hh:mm a"),
		DATE_TIME_SLASH_PATTERN_FULL_12HR("MM/dd/yyyy hh:mm:ss a"),
		DATE_TIME_NOSPACE_PATTERN_12HR("yyyyMMdd_hhmma"),

		//time formats
		TIME_SHORT_PATTERN("h:mm a"),
		TIME_LONG_PATTERN("hh:mm:ss a"),
		TIME_SHORT_NOSPACE("hhmm"),
		TIME_SHORT_24HR_NOSPACE("HHmm"),
		TIME_LONG_24HR_NOSPACE("HHmmss");	
		
		public final String pattern;

	    private DatePatterns(String pattern) {
	        this.pattern = pattern;
	    }
	}
	
	public static void main(String[] args) throws ParseException {
		String def=setDefaultTimeToUTC();
		System.out.println(ConvertZonedDateTimetoDate(ConvertAnyZoneToUTC("01/22/2021 12:43:00","")));
		setDefaultTimeToLocal(def);		
		System.out.println(getStartOfDate("01/22/2021 11:35:00"));
		System.out.println(getStartOfNextDate("01/22/2021 11:35:00"));
		System.out.println(getEndOfDate("01/22/2021 11:35:00"));
		
	}
	/**
	 * Converts given ZonedDateTime format date to Date format date
	 * "YYYY-MM-DDTHH:MMZ" to "EEE MMM DD HH:MM:SS zzz YYYY", Example:  "2021-01-22T07:13Z" to "Fri Jan 22 07:00:00 UTC 2021"
	 * @param Takes in ZonedDateTime zdt as input;
	 * @return Date to use as the end date of a query
	 */
	public static Date ConvertZonedDateTimetoDate(ZonedDateTime zdt) throws ParseException {
		String zdt_str=zdt.toString();
		FastDateFormat fastDateFormat = FastDateFormat.getInstance("YYYY-MM-DD'T'HH:MM'Z'");
		Date myDate = fastDateFormat.parse(zdt_str);
		return myDate;
	}
	
	public static String setDefaultTimeToUTC() {
		String system_default_time_zone=TimeZone.getDefault().getID();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		return system_default_time_zone;
	}
	public static void setDefaultTimeToLocal(String default_time) {
		TimeZone.setDefault(TimeZone.getTimeZone(default_time));
	}
	
	/**
	 * Takes a provided date and figures out the formatting needed to turn the string
	 * into a date
	 * @param theDate string formatted date/time
	 * @return Date object.  Null if unable to match
	 */
	public static Date parseDateUnknownPatternOrString(String theDate) {
		
		if (theDate == null) return null;

		theDate = theDate.replaceAll(",", "");
		//theDate = theDate.trim();
		Date d = null;
		for(DatePatterns dp: DatePatterns.values()) {
			d = formatDate(dp.pattern, theDate);
			if (d != null)
				break;
		}
		return d;
	}
	/**
	 * Converts a String into a Date
	 *
	 * @param datePattern Format of the date
	 * @param dateText text to be converted
	 * @return null if unable to convert
	 */
	public static Date formatDate(String dp, String dateText) {
	
		if (dateText == null || dateText.length() < 4)
			return null;
		// Instantiate the fastDateFormat with the appropriate date/time
		// pattern
		FastDateFormat fastDateFormat = FastDateFormat.getInstance(dp);
		Date myDate = null;

		try {
			// Parse the fastDateFormat object into a date object
			
			myDate = fastDateFormat.parse(dateText);
			
		} catch (ParseException|NullPointerException e) {
			return null;
		}
		
		return myDate;
	}
	/**
	 * The date is adjusted in the field entry and by the amount.  For example, 
	 * if the date is 1/1/2008 and you want to move the month back one month use:
	 * formatDate(date, Calendar.MONTH, -1);
	 * @param d
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date formatDate(Date d, int field, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(d);
		cal.add(field, amount);
		return cal.getTime();
	}
	/**
	 * Checks the submitted string and if it is a date returns true.  
	 * @param sDate
	 * @return
	 */
	public static boolean isDate(String sDate) {
		Date parsedDate = parseDateUnknownPatternOrString(sDate);
		return (parsedDate != null);
	}
	/**
	 * Overloaded to take an object and checks to see if it is a date.
	 * @param oDate
	 * @return
	 */
	public static boolean isDate(Object oDate) {
		if (oDate == null) return isDate("");
		return isDate(oDate.toString().trim());
	}
	/**
	 * Converts a java.util.Date to a java.sql.Date
	 * 
	 * @param val
	 * @return java.sql.Date Object. Null if the value param is null
	 *///keep
	public static java.sql.Date formatSQLDate(java.util.Date val) {
		return formatSQLDate(val, false);
	}

	/**
	 * Converts a java.util.Date to a java.sql.Date
	 * 
	 * @param val
	 * @param defaultToday Uses current date and time if true
	 * @return java.sql.Date Object. Null if the value param is null and defaultNow
	 * is false.  Otherwise, on null, current date and time will be returned
	 */
	public static java.sql.Date formatSQLDate(java.util.Date val, boolean defaultNow) {
		java.sql.Date dt = null;

		if (val != null)
			dt = new java.sql.Date(val.getTime());
		else if (defaultNow)
			dt = new java.sql.Date(new Date().getTime());

		return dt;
	}

	/**
	 * Converts a String into a java.sql.Date Object
	 * 
	 * @param datePattern Format of the date
	 * @param dateText text to be converted
	 * @return null if unable to convert
	 */
	public static java.sql.Date formatSQLDate(String datePattern,String dateText) {
		java.util.Date uDate = formatDate(datePattern, dateText);
		return formatSQLDate(uDate);
	}

	/**
	 * Converts a String into a java.sql.Date Object
	 * 
	 * @param dateText Must be formatted as the default pattern (MM/DD/YYYY)
	 * @return
	 */
	
	public static java.sql.Date formatSQLDate(String dateText) {
		java.util.Date uDate = formatDate(DatePatterns.DATE_SLASH_PATTERN.toString(), dateText);
		return formatSQLDate(uDate);
	}
	/**
	 * Creates a SQL Timestamp out of a String date that matches the supplied pattern
	 * @param datePattern
	 * @param dateText
	 * @return
	 */ 
	public static Timestamp formatTimestamp(String datePattern, String dateText) {
		Date d = formatDate(datePattern,dateText);
		if (d == null) return null;
		else return new Timestamp(d.getTime());
	}
	/**
	 * takes a java.util.Date object and if it's not null converts it to a 
	 * java.sql.Timestamp, for insertion into DBs.
	 * @param dt
	 * @return
	 */
	public static Timestamp formatTimestamp(Date dt) {
		if (dt == null) return null;
		else return new Timestamp(dt.getTime());
	}
	/**
	 * Returns the current month as an integer
	 * @return
	 */
	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * Returns the current year as an integer
	 * @return
	 */
	//keep
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	/**
	 * Returns the current day of Week as an integer; example: 1 for  Monday, 2 for Tuesday......
	 * @return
	 */
	public static int getCurrentDayOfWeek() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * Returns the current day of a month as an integer
	 * @return
	 */
	public static int getCurrentDayOfMonth() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * Returns the current date
	 * @return
	 */
	public static int getCurrentDate() {
		return Calendar.getInstance().get(Calendar.DATE);
	}
	public static Date getEndOfDate(String date) {
		Date d=parseDateUnknownPatternOrString(date);
		return getEndOfDate(d);
	}
	public static Date getEndOfDate(Date d) {	
		if (d == null) d = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	public static Date getStartOfDate(String date) {
		return getStartOfDate(parseDateUnknownPatternOrString(date));
	}
	public static Date getStartOfDate(Date d) {	
		if (d == null) d = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	public static Date getStartOfNextDate(String date) {
		
		return getStartOfNextDate(parseDateUnknownPatternOrString(date));
	}
	public static Date getStartOfNextDate(Date d) {	
		
		if (d == null) d = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static ZonedDateTime ConvertAnyZoneToUTC(String date, String fromZone) {
		return ConvertAnyZoneToUTC(parseDateUnknownPatternOrString(date),fromZone);
	}
	public static ZonedDateTime ConvertAnyZoneToUTC(Date date, String fromZone) {
		String offset = "+05:30";
	    
	    LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), 
                ZoneId.systemDefault());
	    
	   // parse the offset
	    ZoneOffset zoneOffset = ZoneOffset.of(offset);
	    // create an OffsetDateTime using the parsed offset
	    OffsetDateTime odt = OffsetDateTime.of(ldt, zoneOffset);
	
	    // create a ZonedDateTime from the OffsetDateTime and use UTC as time zone
	    ZonedDateTime utcZdt = odt.atZoneSameInstant(ZoneOffset.UTC);
	   
	    return utcZdt;
		
	}
	
}
	
	