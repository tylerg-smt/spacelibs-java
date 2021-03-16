package com.siliconmtn.io.api;

// JEE 7
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

//Junit 5
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

// Spring 5.5.x
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

// JDK 11.x
import java.util.ArrayList;
import java.util.List;

public class RestExceptionHandlerTest {
	
	/**
	 * Test handleMissingServletRequestParameter exception
	 * @throws Exception
	 */
	@Test
	public void testHandleMissingServletRequestParameter() throws Exception {
		RestExceptionHandler  rest = new RestExceptionHandler();
		ResponseEntity<Object> resp = rest.handleMissingServletRequestParameter(new MissingServletRequestParameterException("emailAddress", "String"), null, null, null);
		assert(resp.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("emailAddress parameter is missing"));
	}
	
	/**
	 * Test handleHttpMediaTypeNotSupported exception
	 * @throws Exception
	 */
	@Test
    public void testHandleHttpMediaTypeNotSupported() throws Exception {
		List<MediaType> approved = new ArrayList<>();

		approved.add(MediaType.APPLICATION_PDF);
		approved.add(MediaType.APPLICATION_JSON);
		
		RestExceptionHandler  rest = new RestExceptionHandler();
		ResponseEntity<Object> resp = rest.handleHttpMediaTypeNotSupported(new HttpMediaTypeNotSupportedException(MediaType.IMAGE_PNG, approved), null, null, null);
		
		assert(resp.getStatusCode().equals(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("image/png media type is not supported. Supported media types are application/pdf, application/json"));
    }
	
	/**
	 * Test handleHttpMediaTypeNotSupported exception
	 * @throws Exception
	 */
	@Test
    public void testHandleMethodArgumentNotValid() throws Exception {
		MethodParameter p = mock(MethodParameter.class);
		BindingResult b = mock(BindingResult.class);
		
		RestExceptionHandler  rest = new RestExceptionHandler();
		ResponseEntity<Object> resp = rest.handleMethodArgumentNotValid(new MethodArgumentNotValidException(p, b), null, null, null);
		
		assert(resp.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("Validation error"));
    }
	
	/**
	 * Test handleHttpMediaTypeNotSupported exception
	 * @throws Exception
	 */
	@Test
    public void testHandleConstraintViolation() throws Exception {
		RestExceptionHandler  rest = new RestExceptionHandler();
		ResponseEntity<Object> resp = rest.handleConstraintViolation(new ConstraintViolationException(null));
		
		assert(resp.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("Validation error"));
    }
	
	/**
	 * Test handleEntityNotFound exception
	 * @throws Exception
	 */
	@Test
    public void testHandleApiEntityNotFound() throws Exception {
		RestExceptionHandler  rest = new RestExceptionHandler();
		ResponseEntity<Object> resp = rest.handleEntityNotFound(new ApiRequestException("Entity not found", HttpStatus.NOT_FOUND));
		
		assert(resp.getStatusCode().equals(HttpStatus.NOT_FOUND));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("Entity not found"));
    }
	
	/**
	 * Test handleHttpMessageNotReadable exception
	 * @throws Exception
	 */
	@Test
	@SuppressWarnings("deprecation")
    public void testHandleHttpMessageNotReadable() throws Exception {
		RestExceptionHandler  rest = new RestExceptionHandler();
		ResponseEntity<Object> resp = rest.handleHttpMessageNotReadable(new HttpMessageNotReadableException("Entity not found"), null, null, null);
		
		assert(resp.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("Malformed JSON request"));
    }
	
	/**
	 * Test handleHttpMessageNotWritable exception
	 * @throws Exception
	 */
	@Test
    public void testHandleHttpMessageNotWritable() throws Exception {
		RestExceptionHandler  rest = new RestExceptionHandler();
		ResponseEntity<Object> resp = rest.handleHttpMessageNotWritable(new HttpMessageNotWritableException("Cannot write"), null, null, null);
		
		assert(resp.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("Error writing JSON output"));
    }
	
	/**
	 * Test handleNoHandlerFoundException exception
	 * @throws Exception
	 */
	@Test
    public void testHandleNoHandlerFoundException() throws Exception {
		RestExceptionHandler  rest = new RestExceptionHandler();
		ResponseEntity<Object> resp = rest.handleNoHandlerFoundException(new NoHandlerFoundException("POST", "/api/users", null), null, null, null);
		
		assert(resp.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("Could not find the POST method for URL /api/users"));
    }
	
	/**
	 * Test handleEntityNotFound exception
	 * @throws Exception
	 */
	@Test
    public void testHandleEntityNotFound() throws Exception {
		RestExceptionHandler  rest = new RestExceptionHandler();
		ResponseEntity<Object> resp = rest.handleEntityNotFound(new EntityNotFoundException("Could not fnd requested entity"));
		
		assert(resp.getStatusCode().equals(HttpStatus.NOT_FOUND));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("Unexpected error"));
    }
	
	/**
	 * Test handleDataIntegrityViolation exception
	 * @throws Exception
	 */
	@Test
    public void testHandleDataIntegrityViolation() throws Exception {
		RestExceptionHandler  rest = new RestExceptionHandler();
		ResponseEntity<Object> resp = rest.handleDataIntegrityViolation(new DataIntegrityViolationException("Data error"), null);
		
		assert(resp.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("Unexpected error"));
    }
	
	/**
	 * Test handleDataIntegrityViolation exception
	 * @throws Exception
	 */
	@Test
    public void testHandleDataIntegrityConstraintViolation() throws Exception {
		RestExceptionHandler  rest = new RestExceptionHandler();
		ResponseEntity<Object> resp = rest.handleDataIntegrityViolation(new DataIntegrityViolationException("Data error", 
				new ConstraintViolationException(null)) {private static final long serialVersionUID = 1L;}, null);
		
		assert(resp.getStatusCode().equals(HttpStatus.CONFLICT));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("Database error"));
    }
	
	
	/**
	 * Test handleMethodArgumentTypeMismatch exception
	 * @throws Exception
	 */
	@Test
    public void testHandleMethodArgumentTypeMismatch() throws Exception {
		RestExceptionHandler  rest = new RestExceptionHandler();
		MethodParameter m = mock(MethodParameter.class);
		WebRequest w = mock(WebRequest.class);
		ResponseEntity<Object> resp = rest.handleMethodArgumentTypeMismatch(new MethodArgumentTypeMismatchException("pie", Integer.class
				, "emailAddress", m, new Throwable()), w);
		
		assert(resp.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("The parameter 'emailAddress' of value 'pie' could not be converted to type 'Integer'"));
    }
	
	
	/**
	 * Test handleMethodArgumentTypeMismatch exception
	 * @throws Exception
	 */
	@Test
    public void testHandleMethodArgumentTypeMismatchNoType() throws Exception {
		RestExceptionHandler  rest = new RestExceptionHandler();
		MethodParameter m = mock(MethodParameter.class);
		WebRequest w = mock(WebRequest.class);
		ResponseEntity<Object> resp = rest.handleMethodArgumentTypeMismatch(new MethodArgumentTypeMismatchException("pie", null
				, "emailAddress", m, new Throwable()), w);
		
		assert(resp.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assert(((ApiResponse)resp.getBody()).getMessage().equals("The parameter 'emailAddress' of value 'pie' could not be converted to type ''"));
    }

}
