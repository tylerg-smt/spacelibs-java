package com.siliconmtn.io.api.validation;

// JDK 11.x
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

// Spring 5.5.x
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


// Spacelibs 1.0
import com.siliconmtn.io.api.ApiRequestException;
import com.siliconmtn.io.api.ApiResponse;
import com.siliconmtn.io.api.security.XSSRequestWrapper;
import com.siliconmtn.io.api.validation.factory.ParserFactory;
import com.siliconmtn.io.api.validation.validator.ValidationDTO;

// Lombok 1.18.x
import lombok.extern.log4j.Log4j2;

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
@Log4j2
public class ValidateAop {
	
	@Autowired
	HttpServletRequest request;

	   /** 
	    * This is the method which I would like to execute
	    * before a selected method execution.
	 * @throws Throwable 
	    */
	   @Around("@annotation(com.siliconmtn.io.api.validation.Validate)")
	   public Object beforeAdvice(ProceedingJoinPoint pjp) throws Throwable {
		   
		   Method m = MethodSignature.class.cast(pjp.getSignature()).getMethod();
		   Validate validate = m.getAnnotation(Validate.class);
		   
		   
		   
	        XSSRequestWrapper wrappedRequest = new XSSRequestWrapper(request);
	        
	        wrappedRequest.processStripXSS();
			
		   if (validate != null) {
			   List<ValidationErrorDTO> errors = validateReponse(wrappedRequest.getBody(), m.getName()+ m.getClass().getName());
			   if (errors.size() > 0) {
				   ApiResponse res = new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Request failed validation", new ApiRequestException("Validation Failed"));
				   res.getFailedValidations().addAll(errors);
				   return res;
			   }
		   }
		   
		   return pjp.proceed();
		   
	   } 
	   
	   
	   /**
	    * Load the packager associated with the supplied class and method, package the response body
	    * into ValidationDTOs and validate them to ensure that there are no issues with the data.
	    * @param body The information that is going to be validated
	    * @param key The key that identifies the packager that needs to be created.
	    * @throws ApiRequestException ParserDispatcher failed to create a parser or parse the supplied data in some way.
	    */
	   private List<ValidationErrorDTO> validateReponse(String body, String key) throws ApiRequestException {
		   
		   ParserFactory pFact = new ParserFactory();
		   List<ValidationDTO> fields;
			try {
				fields = pFact.parserDispatcher(key).requestParser(body.getBytes());
			} catch (Exception e) {
				throw new ApiRequestException("Data validation preperation failed.", e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
			} 
		   
		   return ValidationUtil.validateData(fields);
	   }

}
