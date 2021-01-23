package com.smt.weather;

// JDK 11.x
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

// Junit 5
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/****************************************************************************
 * <b>Title</b>: SolarEventCalculatorTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> Tests the SolarEventCalculator
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 22, 2021
 * @updates:
 ****************************************************************************/
class SolarEventCalculatorTest {
	
	private Coordinate coord;
	private String timeZoneIdentifier;
	private SolarEventCalculator calc;
	private Calendar testDate;

	@BeforeEach
	void setUpBeforeEach() throws Exception {
		timeZoneIdentifier = "America/Denver";
		
		coord = new Coordinate(39.742043, -104.991531);
		calc = new SolarEventCalculator(coord, TimeZone.getTimeZone(timeZoneIdentifier));
		testDate = new GregorianCalendar(2021, 2, 1);
		
	}
	
	/**
	 * Test method for {@link com.smt.weather.SolarEventCalculator#SolarEventCalculator(com.smt.weather.Coordinate, java.lang.String)}.
	 */
	@Test
	void testSolarEventCalculatorCoordinateString() {
		timeZoneIdentifier = "America/Denver";
		
		coord = new Coordinate(39.742043, -104.991531);
		SolarEventCalculator calc = new SolarEventCalculator(coord, timeZoneIdentifier);
		TimeZone tz = TimeZone.getTimeZone(timeZoneIdentifier);
		assertEquals(coord, calc.getLocation());
		assertEquals(tz, calc.getTimeZone());
	}

	/**
	 * Test method for {@link com.smt.weather.SolarEventCalculator#SolarEventCalculator(com.smt.weather.Coordinate, java.util.TimeZone)}.
	 */
	@Test
	void testSolarEventCalculatorCoordinateTimeZone() {
		TimeZone tz = TimeZone.getTimeZone("America/Denver");
		coord = new Coordinate(39.742043, -104.991531);
		SolarEventCalculator calc = new SolarEventCalculator(coord, tz);
		
		assertEquals(coord, calc.getLocation());
		assertEquals(tz, calc.getTimeZone());
	}

	/**
	 * Test method for {@link com.smt.weather.SolarEventCalculator#computeSunriseTime(com.smt.weather.Zenith, java.util.Calendar)}.
	 */
	@Test
	void testComputeSunriseTime() {
		//String time = calc.computeSunriseTime(Zenith.OFFICIAL, testDate);
		assertEquals("06:12", calc.computeSunriseTime(Zenith.OFFICIAL, testDate));
	}

	/**
	 * Test method for {@link com.smt.weather.SolarEventCalculator#computeSunriseCalendar(com.smt.weather.Zenith, java.util.Calendar)}.
	 */
	@Test
	void testComputeSunriseCalendar() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.weather.SolarEventCalculator#computeSunsetTime(com.smt.weather.Zenith, java.util.Calendar)}.
	 */
	@Test
	void testComputeSunsetTime() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.weather.SolarEventCalculator#computeSunsetCalendar(com.smt.weather.Zenith, java.util.Calendar)}.
	 */
	@Test
	void testComputeSunsetCalendar() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.weather.SolarEventCalculator#getLocalTimeAsCalendar(java.math.BigDecimal, java.util.Calendar)}.
	 */
	@Test
	void testGetLocalTimeAsCalendar() {
		fail("Not yet implemented");
	}

}
