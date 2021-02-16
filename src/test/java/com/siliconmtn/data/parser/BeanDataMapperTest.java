package com.siliconmtn.data.parser;

// Junit 5
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

// JDK 11.x
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

// SPace Libs 1.x
import com.siliconmtn.data.format.DateFormat.DatePattern;

/****************************************************************************
 * <b>Title</b>: BeanDataMapperTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 28, 2021
 * @updates:
 ****************************************************************************/
class BeanDataMapperTest {
	
	// Members
	private TestDataBeanVO dbvo;
	private Map<String, String[]> map;
	
	@Mock
	private HttpServletRequest req;
	
	@BeforeEach
	void setUpBeforeEach() throws Exception {
		req = Mockito.mock(HttpServletRequest.class);
		map = new HashMap<>();
		map.put("name", new String[] {"SMT"});
		map.put("someNumber", new String[] {"1"});
		map.put("currDate", new String[] {"2020-01-01"});
		map.put("datePattern", new String[] {DatePattern.DATE_DASH.name()});
		map.put("arrNames", new String[] {"one", "two", "three"});
		map.put("names", new String[] {"James", "Mary", "Stef"});
		map.put("names_test", new String[] {"Sue", "Cindy", "Quianna", "Kate"});
		
		when(req.getParameterMap()).thenReturn(map);
		
		dbvo = new TestDataBeanVO();
	}

	/**
	 * Test method for {@link com.siliconmtn.data.parser.BeanDataMapper#parseBean(java.lang.Object, java.util.Map)}.
	 */
	@Test
	void testParseBeanObjectMapOfStringString() {
		BeanDataMapper.parseBean(dbvo, map);
		assertEquals("SMT", dbvo.getName());
		assertEquals(3, dbvo.getNames().size());
	}

	/**
	 * Test method for {@link com.siliconmtn.data.parser.BeanDataMapper#parseBean(java.lang.Object, java.util.Map, java.lang.String)}.
	 */
	@Test
	void testParseBeanObjectMapOfStringStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#toString()}.
	 */
	@Test
	void testToString() {
		fail("Not yet implemented");
	}

}
