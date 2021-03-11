package com.siliconmtn.io.api.validation;

// JDK 11.x
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.siliconmtn.io.api.validation.ValidationErrorDTO.ValidationError;

// Lombok 1.18.x
import lombok.extern.log4j.Log4j2;

/****************************************************************************
 * <b>Title</b>: ValidateAop.java
 * <b>Project</b>: planit-api
 * <b>Description: </b> Aspect that validates the data passed into the server
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
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
	   public Object beforeAdvice(ProceedingJoinPoint jp) throws Throwable {
		   
		   Method m = MethodSignature.class.cast(jp.getSignature()).getMethod();
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
		   
		   return jp.proceed();
		   
	   } 
	   
	   
	   /**
	    * Load the packager associated with the supplied class and method, package the response body
	    * into ValidationDTOs and validate them to ensure that there are no issues with the data.
	    * @param body The information that is going to be validated
	    * @param key The key that identifies the packager that needs to be created.
	    * @throws ApiRequestException When there are validation errors this is thrown with information on what failed and why.
	    */
	   private List<ValidationErrorDTO> validateReponse(Object body, String key) throws ApiRequestException {
		   List<ValidationErrorDTO> errors = new ArrayList<>();
		   
		   if (Math.random() > .5) {
			   errors.add(ValidationErrorDTO.builder().errorMessage("Failed because math hates you").validationError(ValidationError.RANGE).value("test").elementId("test_id").build());
		   }
		   
		   return errors;
	   }

}
