package com.siliconmtn.io.api.validation;

// JDK 11.x
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
// Spring 5.5.x
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


// Spacelibs 1.0
import com.siliconmtn.io.api.ApiRequestException;
import com.siliconmtn.io.api.security.XSSRequestWrapper;
import com.siliconmtn.io.api.validation.factory.ParserFactory;
import com.siliconmtn.io.api.validation.factory.ParserIntfc;
import com.siliconmtn.io.api.validation.validator.ValidationDTO;

/****************************************************************************
 * <b>Title</b>: ValidateAop.java
 * <b>Project</b>: Spacelibs-java
 * <b>Description: </b> Aspect that validates the data passed into the server
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Eric Damschroder
 * @version 3.0
 * @since Mar 4, 2021
 * @updates:
 ****************************************************************************/
@Aspect
@Component
public class ValidateAop {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ParserFactory pFact;

	   /** 
	    * This is the method which I would like to execute
	    * before a selected method execution.
	 * @throws Throwable 
	    */
	   @Before("@annotation(com.siliconmtn.io.api.validation.Validate)")
	   public Object beforeAdvice(JoinPoint pjp) throws Throwable {
		   Method m = MethodSignature.class.cast(pjp.getSignature()).getMethod();
		   Validate validate = m.getAnnotation(Validate.class);
		   
		   
	        XSSRequestWrapper wrappedRequest = new XSSRequestWrapper(request);
	        
	        wrappedRequest.processStripXSS();
			
		   if (validate != null) {
			   List<ValidationErrorDTO> errors = validateReponse(wrappedRequest.getBody(),  m.getDeclaringClass().getName() + "." + m.getName());
			   if (errors.size() > 0) {
				   ApiRequestException ex = new ApiRequestException("Failed to validate request");
				   ex.addAllFailedValidation(errors);
				   throw ex;
			   }
		   }
		return null;
		   
	   } 
	   
	   
	   /**
	    * Load the packager associated with the supplied class and method, package the response body
	    * into ValidationDTOs and validate them to ensure that there are no issues with the data.
	    * @param body The information that is going to be validated
	    * @param key The key that identifies the packager that needs to be created.
	    * @throws ApiRequestException ParserDispatcher failed to create a parser or parse the supplied data in some way.
	    */
	   private List<ValidationErrorDTO> validateReponse(String body, String key) throws ApiRequestException {
		   
		   List<ValidationDTO> fields;
			try {
				ParserIntfc parser =  pFact.parserDispatcher(key);
				
				if (parser == null) return Collections.emptyList();
				
				fields = parser.requestParser(body.getBytes());
			} catch (Exception e) {
				throw new ApiRequestException("Data validation preperation failed.", e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
			} 
			
		   return ValidationUtil.validateData(fields);
	   }

}
