package com.smt.data.format;

// JDK 11.x
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

// Spacelibs 1.x
import com.smt.data.bean.GenericVO;

/****************************************************************************
 * <b>Title</b>: TimeZoneManager.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> Manages the data and interfacing into the time zones
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 1.0
 * @since Jan 21, 2021
 * @updates:
 ****************************************************************************/
public class TimeZoneManager {
	/**
	 * Assigns the time zones and their associate meta data
	 */
	public static final Map<String, TimeZoneVO> TIME_ZONES = new LinkedHashMap<>() {
		private static final long serialVersionUID = -6567107924085271771L; {
			put("Asia/Kabul", new TimeZoneVO("Asia/Kabul", "Afghanistan Standard Time", "(GMT +04:30) Kabul", ""));
			put("America/Anchorage", new TimeZoneVO("America/Anchorage", "Alaskan Standard Time", "(GMT -09:00) Alaska", ""));
			put("Asia/Riyadh", new TimeZoneVO("Asia/Riyadh", "Arab Standard Time", "(GMT +03:00) Kuwait, Riyadh", ""));
			put("Asia/Dubai", new TimeZoneVO("Asia/Dubai", "Arabian Standard Time", "(GMT +04:00) Abu Dhabi, Muscat", ""));
			put("Asia/Baghdad", new TimeZoneVO("Asia/Baghdad", "Arabic Standard Time", "(GMT +03:00) Baghdad", ""));
			put("America/Buenos_Aires", new TimeZoneVO("America/Buenos_Aires", "Argentina Standard Time", "(GMT -03:00) Buenos Aires", ""));
			put("America/Halifax", new TimeZoneVO("America/Halifax", "Atlantic Standard Time", "(GMT -04:00) Atlantic Time (Canada)", ""));
			put("Australia/Darwin", new TimeZoneVO("Australia/Darwin", "AUS Central Standard Time", "(GMT +09:30) Darwin", ""));
			put("Australia/Sydney", new TimeZoneVO("Australia/Sydney", "AUS Eastern Standard Time", "(GMT +10:00) Canberra, Melbourne, Sydney", ""));
			put("Asia/Baku", new TimeZoneVO("Asia/Baku", "Azerbaijan Standard Time", "(GMT +04:00) Baku", ""));
			put("Atlantic/Azores", new TimeZoneVO("Atlantic/Azores", "Azores Standard Time", "(GMT -01:00) Azores", ""));
			put("Asia/Dhaka", new TimeZoneVO("Asia/Dhaka", "Bangladesh Standard Time", "(GMT +06:00) Dhaka", ""));
			put("America/Regina", new TimeZoneVO("America/Regina", "Canada Central Standard Time", "(GMT -06:00) Saskatchewan", ""));
			put("Atlantic/Cape_Verde", new TimeZoneVO("Atlantic/Cape_Verde", "Cape Verde Standard Time", "(GMT -01:00) Cape Verde Is.", ""));
			put("Asia/Yerevan", new TimeZoneVO("Asia/Yerevan", "Caucasus Standard Time", "(GMT +04:00) Yerevan", ""));
			put("Australia/Adelaide", new TimeZoneVO("Australia/Adelaide", "Cen. Australia Standard Time", "(GMT +09:30) Adelaide", ""));
			put("America/Guatemala", new TimeZoneVO("America/Guatemala", "Central America Standard Time", "(GMT -06:00) Central America", ""));
			put("Asia/Almaty", new TimeZoneVO("Asia/Almaty", "Central Asia Standard Time", "(GMT +06:00) Astana", ""));
			put("America/Cuiaba", new TimeZoneVO("America/Cuiaba", "Central Brazilian Standard Time", "(GMT -04:00) Cuiaba", ""));
			put("Europe/Budapes", new TimeZoneVO("Europe/Budapest", "Central Europe Standard Time", "(GMT +01:00) Belgrade, Bratislava, Budapest, Ljubljana, Prague", ""));
			put("Europe/Warsaw", new TimeZoneVO("Europe/Warsaw", "Central European Standard Time", "(GMT +01:00) Sarajevo, Skopje, Warsaw, Zagreb", ""));
			put("Pacific/Guadalcanal", new TimeZoneVO("Pacific/Guadalcanal", "Central Pacific Standard Time", "(GMT +11:00) Solomon Is., New Caledonia", ""));
			put("America/Mexico_City", new TimeZoneVO("America/Mexico_City", "Central Standard Time (Mexico)", "(GMT -06:00) Guadalajara, Mexico City, Monterrey", ""));
			put("America/Chicago", new TimeZoneVO("America/Chicago", "Central Standard Time", "(GMT -06:00) Central Time (US & Canada)", ""));
			put("Asia/Shanghai", new TimeZoneVO("Asia/Shanghai", "China Standard Time", "(GMT +08:00) Beijing, Chongqing, Hong Kong, Urumqi", ""));
			put("Etc/GMT+12", new TimeZoneVO("Etc/GMT+12", "Dateline Standard Time", "(GMT -12:00) International Date Line West", ""));
			put("Africa/Nairobi", new TimeZoneVO("Africa/Nairobi", "E. Africa Standard Time", "(GMT +03:00) Nairobi", ""));
			put("Australia/Brisbane", new TimeZoneVO("Australia/Brisbane", "E. Australia Standard Time", "(GMT +10:00) Brisbane", ""));
			put("Europe/Minsk", new TimeZoneVO("Europe/Minsk", "E. Europe Standard Time", "(GMT +02:00) Minsk", ""));
			put("America/Sao_Paulo", new TimeZoneVO("America/Sao_Paulo", "E. South America Standard Time", "(GMT -03:00) Brasilia", ""));
			put("America/New_York", new TimeZoneVO("America/New_York", "Eastern Standard Time", "(GMT -05:00) Eastern Time (US & Canada)", ""));
			put("Africa/Cairo", new TimeZoneVO("Africa/Cairo", "Egypt Standard Time", "(GMT +02:00) Cairo", ""));
			put("Asia/Yekaterinburg", new TimeZoneVO("Asia/Yekaterinburg", "Ekaterinburg Standard Time", "(GMT +05:00) Ekaterinburg", ""));
			put("Pacific/Fiji", new TimeZoneVO("Pacific/Fiji", "Fiji Standard Time", "(GMT +12:00) Fiji, Marshall Is.", ""));
			put("Europe/Kiev", new TimeZoneVO("Europe/Kiev", "FLE Standard Time", "(GMT +02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius", ""));
			put("Asia/Tbilisi", new TimeZoneVO("Asia/Tbilisi", "Georgian Standard Time", "(GMT +04:00) Tbilisi", ""));
			put("Europe/London", new TimeZoneVO("Europe/London", "GMT Standard Time", "(GMT) Dublin, Edinburgh, Lisbon, London", ""));
			put("America/Godthab", new TimeZoneVO("America/Godthab", "Greenland Standard Time", "(GMT -03:00) Greenland", ""));
			put("Atlantic/Reykjavik", new TimeZoneVO("Atlantic/Reykjavik", "Greenwich Standard Time", "(GMT) Monrovia, Reykjavik", ""));
			put("Europe/Istanbul", new TimeZoneVO("Europe/Istanbul", "GTB Standard Time", "(GMT +02:00) Athens, Bucharest, Istanbul", ""));
			put("Pacific/Honolulu", new TimeZoneVO("Pacific/Honolulu", "Hawaiian Standard Time", "(GMT -10:00) Hawaii", ""));
			put("Asia/Calcutta", new TimeZoneVO("Asia/Calcutta", "India Standard Time", "(GMT +05:30) Chennai, Kolkata, Mumbai, New Delhi", ""));
			put("Asia/Tehran", new TimeZoneVO("Asia/Tehran", "Iran Standard Time", "(GMT +03:30) Tehran", ""));
			put("Asia/Jerusalem", new TimeZoneVO("Asia/Jerusalem", "Israel Standard Time", "(GMT +02:00) Jerusalem", ""));
			put("Asia/Amman", new TimeZoneVO("Asia/Amman", "Jordan Standard Time", "(GMT +02:00) Amman", ""));
			put("Asia/Kamchatka", new TimeZoneVO("Asia/Kamchatka", "Kamchatka Standard Time", "(GMT +12:00) Petropavlovsk-Kamchatsky - Old", ""));
			put("Asia/Seoul", new TimeZoneVO("Asia/Seoul", "Korea Standard Time", "(GMT +09:00) Seoul", ""));
			put("Asia/Magadan", new TimeZoneVO("Asia/Magadan", "Magadan Standard Time", "(GMT +11:00) Magadan", ""));
			put("Indian/Mauritius", new TimeZoneVO("Indian/Mauritius", "Mauritius Standard Time", "(GMT +04:00) Port Louis", ""));
			put("Etc/GMT+2", new TimeZoneVO("Etc/GMT+2", "Mid-Atlantic Standard Time", "(GMT -02:00) Mid-Atlantic", ""));
			put("Asia/Beirut", new TimeZoneVO("Asia/Beirut", "Middle East Standard Time", "(GMT +02:00) Beirut", ""));
			put("America/Montevideo", new TimeZoneVO("America/Montevideo", "Montevideo Standard Time", "(GMT -03:00) Montevideo", ""));
			put("Africa/Casablanca", new TimeZoneVO("Africa/Casablanca", "Morocco Standard Time", "(GMT) Casablanca", ""));
			put("America/Chihuahua", new TimeZoneVO("America/Chihuahua", "Mountain Standard Time (Mexico)", "(GMT -07:00) Chihuahua, La Paz, Mazatlan", "MST"));
			put("America/Denver", new TimeZoneVO("America/Denver", "Mountain Standard Time", "(GMT -07:00) Mountain Time (US & Canada)", "MST"));
			put("Asia/Rangoon", new TimeZoneVO("Asia/Rangoon", "Myanmar Standard Time", "(GMT +06:30) Yangon (Rangoon)", ""));
			put("Asia/Novosibirsk", new TimeZoneVO("Asia/Novosibirsk", "N. Central Asia Standard Time", "(GMT +06:00) Novosibirsk", ""));
			put("Africa/Windhoek", new TimeZoneVO("Africa/Windhoek", "Namibia Standard Time", "(GMT +02:00) Windhoek", ""));
			put("Asia/Katmandu", new TimeZoneVO("Asia/Katmandu", "Nepal Standard Time", "(GMT +05:45) Kathmandu", ""));
			put("Pacific/Auckland", new TimeZoneVO("Pacific/Auckland", "New Zealand Standard Time", "(GMT +12:00) Auckland, Wellington", ""));
			put("America/St_Johns", new TimeZoneVO("America/St_Johns", "Newfoundland Standard Time", "(GMT -03:30) Newfoundland", ""));
			put("Asia/Irkutsk", new TimeZoneVO("Asia/Irkutsk", "North Asia East Standard Time", "(GMT +08:00) Irkutsk", ""));
			put("Asia/Krasnoyarsk", new TimeZoneVO("Asia/Krasnoyarsk", "North Asia Standard Time", "(GMT +07:00) Krasnoyarsk", ""));
			put("America/Santiago", new TimeZoneVO("America/Santiago", "Pacific SA Standard Time", "(GMT -04:00) Santiago", ""));
			put("America/Tijuana", new TimeZoneVO("America/Tijuana", "Pacific Standard Time (Mexico)", "(GMT -08:00) Baja California", ""));
			put("America/Los_Angeles", new TimeZoneVO("America/Los_Angeles", "Pacific Standard Time", "(GMT -08:00) Pacific Time (US & Canada)", ""));
			put("Asia/Karachi", new TimeZoneVO("Asia/Karachi", "Pakistan Standard Time", "(GMT +05:00) Islamabad, Karachi", ""));
			put("America/Asuncion", new TimeZoneVO("America/Asuncion", "Paraguay Standard Time", "(GMT -04:00) Asuncion", ""));
			put("Europe/Paris", new TimeZoneVO("Europe/Paris", "Romance Standard Time", "(GMT +01:00) Brussels, Copenhagen, Madrid, Paris", ""));
			put("Europe/Moscow", new TimeZoneVO("Europe/Moscow", "Russian Standard Time", "(GMT +03:00) Moscow, St. Petersburg, Volgograd", ""));
			put("America/Cayenne", new TimeZoneVO("America/Cayenne", "SA Eastern Standard Time", "(GMT -03:00) Cayenne, Fortaleza", ""));
			put("America/Bogota", new TimeZoneVO("America/Bogota", "SA Pacific Standard Time", "(GMT -05:00) Bogota, Lima, Quito", ""));
			put("America/La_Paz", new TimeZoneVO("America/La_Paz", "SA Western Standard Time", "(GMT -04:00) Georgetown, La Paz, Manaus, San Juan", ""));
			put("Pacific/Samoa", new TimeZoneVO("Pacific/Samoa", "Samoa Standard Time", "(GMT -11:00) Samoa", ""));
			put("Asia/Bangkok", new TimeZoneVO("Asia/Bangkok", "SE Asia Standard Time", "(GMT +07:00) Bangkok, Hanoi, Jakarta", ""));
			put("Asia/Singapore", new TimeZoneVO("Asia/Singapore", "Singapore Standard Time", "(GMT +08:00) Kuala Lumpur, Singapore", ""));
			put("Africa/Johannesburg", new TimeZoneVO("Africa/Johannesburg", "South Africa Standard Time", "(GMT +02:00) Harare, Pretoria", ""));
			put("Asia/Colombo", new TimeZoneVO("Asia/Colombo", "Sri Lanka Standard Time", "(GMT +05:30) Sri Jayawardenepura", ""));
			put("Asia/Damascus", new TimeZoneVO("Asia/Damascus", "Syria Standard Time", "(GMT +02:00) Damascus", ""));
			put("Asia/Taipei", new TimeZoneVO("Asia/Taipei", "Taipei Standard Time", "(GMT +08:00) Taipei", ""));
			put("Australia/Hobart", new TimeZoneVO("Australia/Hobart", "Tasmania Standard Time", "(GMT +10:00) Hobart", ""));
			put("Asia/Tokyo", new TimeZoneVO("Asia/Tokyo", "Tokyo Standard Time", "(GMT +09:00) Osaka, Sapporo, Tokyo", ""));
			put("Pacific/Tongatapu", new TimeZoneVO("Pacific/Tongatapu", "Tonga Standard Time", "(GMT +13:00) Nuku'alofa", ""));
			put("Asia/Ulaanbaatar", new TimeZoneVO("Asia/Ulaanbaatar", "Ulaanbaatar Standard Time", "(GMT +08:00) Ulaanbaatar", ""));
			put("America/Indianapolis", new TimeZoneVO("America/Indianapolis", "US Eastern Standard Time", "(GMT -05:00) Indiana (East)", "EST"));
			put("America/Phoenix", new TimeZoneVO("America/Phoenix", "US Mountain Standard Time", "(GMT -07:00) Arizona", "MST"));
			put("Etc/GMT", new TimeZoneVO("Etc/GMT", "GMT", "(GMT) Coordinated Universal Time", "GMT"));
			put("Etc/GMT-12", new TimeZoneVO("Etc/GMT-12", "GMT +12", "(GMT +12:00) Coordinated Universal Time+12", ""));
			put("Etc/GMT+2", new TimeZoneVO("Etc/GMT+2", "GMT -02", "(GMT -02:00) Coordinated Universal Time-02", ""));
			put("Etc/GMT+11", new TimeZoneVO("Etc/GMT+11", "GMT -11", "(GMT -11:00) Coordinated Universal Time-11", ""));
			put("America/Caracas", new TimeZoneVO("America/Caracas", "Venezuela Standard Time", "(GMT -04:30) Caracas", ""));
			put("Asia/Vladivostok", new TimeZoneVO("Asia/Vladivostok", "Vladivostok Standard Time", "(GMT +10:00) Vladivostok", ""));
			put("Australia/Perth", new TimeZoneVO("Australia/Perth", "W. Australia Standard Time", "(GMT +08:00) Perth", ""));
			put("Africa/Lagos", new TimeZoneVO("Africa/Lagos", "W. Central Africa Standard Time", "(GMT +01:00) West Central Africa", ""));
			put("Europe/Berlin", new TimeZoneVO("Europe/Berlin", "W. Europe Standard Time", "(GMT +01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna", ""));
			put("Asia/Tashkent", new TimeZoneVO("Asia/Tashkent", "West Asia Standard Time", "(GMT +05:00) Tashkent", ""));
			put("Pacific/Port_Moresby", new TimeZoneVO("Pacific/Port_Moresby", "West Pacific Standard Time", "(GMT +10:00) Guam, Port Moresby", ""));
			put("Asia/Yakutsk", new TimeZoneVO("Asia/Yakutsk", "Yakutsk Standard Time", "(GMT +09:00) Yakutsk", ""));
		}
	};

	/**
	 * Not used due to static methods only allowed
	 */
	private TimeZoneManager() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<GenericVO> getTimeZoneList() {
		List<GenericVO> zones = new ArrayList<>();
		for (Map.Entry<String, TimeZoneVO> entry : TIME_ZONES.entrySet()) {
			zones.add(new GenericVO(entry.getKey(), entry.getValue().getName()));
		}
		
		return zones;
	}
	
	/**
	 * Retrieves the extended data for a given time zone id
	 * @param timeZoneId
	 * @return
	 */
	public static TimeZoneVO getTimeZone(String timeZoneId) {
		return TIME_ZONES.get(timeZoneId);
	}

	/**
	 * Returns all of the TimeZone VOs that match the given iso code
	 * @param isoCode
	 * @return
	 */
	public static List<TimeZoneVO> getTimeZoneByIsoCode(String isoCode) {
		if (StringUtils.isEmpty(isoCode)) return null;
		
		List<TimeZoneVO> zones = new ArrayList<>();
		for (Map.Entry<String, TimeZoneVO> entry : TIME_ZONES.entrySet()) {
			if (isoCode.equalsIgnoreCase(entry.getValue().getIsoCode()))
				zones.add(entry.getValue());
		}
		
		return zones;

	}
}
