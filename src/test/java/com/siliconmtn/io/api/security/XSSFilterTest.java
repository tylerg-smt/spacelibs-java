package com.siliconmtn.io.api.security;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

// Junit 5
import org.junit.jupiter.api.Test;

public class XSSFilterTest {
	
	/**
	 * Ensure that the filter can run without throwing any errors, 
	 * actual XSS Striping testing comes from it's own test class.
	 * @throws Exception
	 */
	@Test
	public void testXSSFilter() throws Exception {
		
		XSSFilter xss = new XSSFilter();

        HttpServletRequest request = mock(HttpServletRequest.class);
        XSSRequestWrapper wrappedRequest = mock(XSSRequestWrapper.class);
        ServletResponse response = mock(ServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        
        
        String test = "<span>test</span>";
        Reader inputString = new StringReader(test);
        BufferedReader reader = new BufferedReader(inputString);
        
        when(request.getReader()).thenReturn(reader);
        when(wrappedRequest.getReader()).thenReturn(reader);

        xss.doFilter(request, response, chain);
        
	}
	
}
