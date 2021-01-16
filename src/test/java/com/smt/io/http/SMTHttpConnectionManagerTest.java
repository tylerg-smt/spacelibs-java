package com.smt.io.http;

import java.net.HttpURLConnection;
// JDK 11.x
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// JUnit5
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.smt.io.http.SMTHttpConnectionManager.HttpConnectionType;

/****************************************************************************
 * <b>Title</b>: SMTHttpConnectionManagerTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> Tests the HttpConnectionManager
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 15, 2021
 * @updates:
 ****************************************************************************/
class SMTHttpConnectionManagerTest {
	
	/**
	 * Request parameters for the test
	 */
	static Map<String, Object> params;
	Map<String, String> headers;
	Map<String, String> cookies;
	SMTHttpConnectionManager connection;
	String url = "https://www.siliconmtn.com";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		params = new HashMap<>();
		
		List<String> values = new ArrayList<>();
		values.add("one");
		values.add("two");
		values.add("three");
		
		params.put("counter", values);
		params.put("arrCtr", new String[] {"alpha", "brave", "charlie"});
		params.put("name","James");
		params.put("age","30");
	}
	
	@BeforeEach
	void setUpBeforeEach() throws Exception {
		connection = new SMTHttpConnectionManager();
		
		headers = new HashMap<>();
		headers.put("Host", "www.siliconmtn.com");
		headers.put("Referer", "www.google.com");
		headers.put("User-Agent", "Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail");
		
		cookies = new HashMap<>();
		cookies.put("JSESSION_ID", "12345678");
		cookies.put("AWSALB", "AWS_COOKIE");
		cookies.put("AWSALBCORS", "AWS_COR_COOKIE");
	}
	
	/**
	 * Initializes and turns cokkie management on and off
	 */
	@Test
	void testSMTHttpConnectionManagerBoolean() {
		new SMTHttpConnectionManager(true);
		new SMTHttpConnectionManager(false);
	}

	/**
	 * Validates the constructor with an SSL Socket factory
	 */
	@Test
	void testSMTHttpConnectionManagerSSLSocketFactory() {
		SMTHttpConnectionManager conn = new SMTHttpConnectionManager(null);
	}

	/**
	 * Retrieves the data form the end server.  Needs an http mock
	 */
	@Test
	void testGetRequestData() throws Exception {
		assertEquals(0, connection.getRequestData(url, null, HttpConnectionType.GET).length);
	}

	/**
	 * Converts the map of past params into a post pfrmatted data string
	 */
	@Test
	void testConvertPostData() {
		SMTHttpConnectionManager conn = new SMTHttpConnectionManager(true);
		assertTrue(new String(conn.convertPostData(params)).contains("one"));
		assertEquals("", new String(conn.convertPostData(null)));
		assertFalse(new String(conn.convertPostData(new HashMap<>())).contains("one"));
	}

	/**
	 * Tests the ssl socket informaiton timeout metadata
	 * @throws Exception
	 */
	@Test
	void testSetSslSocketFactory() {
		fail("Not yet implemented");
	}

	/**
	 * Tests the connection timeout metadata
	 * @throws Exception
	 */
	@Test
	public void testGetConnectionTimeout() throws Exception {
		connection.setConnectionTimeout(5);
		assertEquals(5, connection.getConnectionTimeout());
	}

	/**
	 * Tests the connection meta data around following redirects
	 * @throws Exception
	 */
	@Test
	public void testIsFollowRedirects() throws Exception {
		connection.setFollowRedirects(true);
		assertTrue(connection.isFollowRedirects());
	}

	/**
	 * Tests the connection request headers
	 * @throws Exception
	 */
	@Test
	public void testGetRequestHeaders() throws Exception {
		connection.setRequestHeaders(headers);
		assertEquals(3, connection.getRequestHeaders().size());
	}

	/**
	 * Test the response code from the request
	 * @throws Exception
	 */
	@Test
	public void testGetResponseCode() throws Exception {
		assertEquals(0, connection.getResponseCode());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetCookies() throws Exception {
		connection.setCookies(cookies);
		assertEquals(3, connection.getCookies().size());
		
		// Loop the original cookies and make sure they are all returned
		for(String s: cookies.values()) {
			assertTrue(connection.getCookies().values().contains(s));
		}
		
		cookies = null;
		connection.setCookies(cookies);
		assertEquals(0, connection.getCookies().size());
		
		HttpURLConnection cConn = null;
		connection.assignCookies(cConn);
	}

	/**
	 * Gets the values in the headerMap
	 * @throws Exception
	 */
	@Test
	public void testGetHeaderMap() throws Exception {
		connection.setHeaderMap(cookies);
		assertEquals(3, connection.getHeaderMap().size());
		
		// Loop the original cookies and make sure they are all returned
		for(String s: cookies.values()) {
			assertTrue(connection.getHeaderMap().values().contains(s));
		}
		
		connection.setHeaderMap(null);
		assertEquals(0, connection.getHeaderMap().size());
	}

	/**
	 * Assigns and gets the maximum nuber of redirects to follow on a request
	 * @throws Exception
	 */
	@Test
	public void testGetRedirectLimit() throws Exception {
		connection.setRedirectLimit(5);
		assertEquals(5, connection.getRedirectLimit());
	}

	/**
	 * tests getting and setting the cookie handler
	 * @throws Exception
	 */
	@Test
	public void testIsUseCookieHandler() throws Exception {
		connection.setUseCookieHandler(true);
		assertTrue(connection.isUseCookieHandler());
	}

	@Test
	public void testGetConnectionStream() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testConnectStream() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testCreateURL() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testCreateConnection() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testExecuteConnection() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testInitConnection() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testStoreCookies() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
		public void testAssignCookiesHttpURLConnection() throws Exception {
			throw new RuntimeException("not yet implemented");
		}

	@Test
	public void testSetRequestHeadersHttpURLConnection() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testConnect() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

}
