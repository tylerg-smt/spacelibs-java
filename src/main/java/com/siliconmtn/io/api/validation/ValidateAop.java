package com.siliconmtn.io.api.validation;

import java.io.IOException;
// JDK 11.x
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

// Spring 5.5.x
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.siliconmtn.data.text.StringUtil;
// Spacelibs 1.0
import com.siliconmtn.io.api.ApiRequestException;
import com.siliconmtn.io.api.security.XSSRequestWrapper;
import com.siliconmtn.io.api.validation.packager.PackagerFactory;
import com.siliconmtn.io.api.validation.packager.PackagerIntfc;
import com.siliconmtn.io.api.validation.validator.ValidationDTO;

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

	   /** 
	    * This is the method which I would like to execute
	    * before a selected method execution.
	    */
	   @Before("@annotation(com.siliconmtn.io.api.validation.Validate) && args(.., @Request request)")
	   public void aroundAdvice(JoinPoint jp, Object request) throws ApiRequestException {
		   Method m = MethodSignature.class.cast(jp.getSignature()).getMethod();
		   Validate validate = m.getAnnotation(Validate.class);
		   
	        XSSRequestWrapper wrappedRequest = new XSSRequestWrapper((HttpServletRequest) request);

	        String body;
			try {
				body = IOUtils.toString(wrappedRequest.getReader());
		        if (!StringUtil.isEmpty(body)) {
		            body = XSSRequestWrapper.stripXSS(body);
		            wrappedRequest.resetInputStream(body.getBytes());
		        }
			} catch (IOException e) {
				throw new ApiRequestException(e.getCause(), HttpStatus.UNPROCESSABLE_ENTITY);
			}
		   
		   if (validate != null) {
			   validateReponse(body, m.getName()+ m.getClass().getName());
		   }
		   
		   log.info("################## ");
	   } 
	   
	   
	   /**
	    * Load the packager associated with the supplied class and method, package the response body
	    * into ValidationDTOs and validate them to ensure that there are no issues with the data.
	    * @param body The information that is going to be validated
	    * @param key The key that identifies the packager that needs to be created.
	    * @throws ApiRequestException When there are validation errors this is thrown with information on what failed and why.
	    */
	   private void validateReponse(Object body, String key) throws ApiRequestException {
		   PackagerIntfc packager = PackagerFactory.getPackager(key);
		   List<ValidationDTO> fields = packager.packageDTOs(body);
		   List<ValidationErrorDTO> errors = ValidationUtil.validateData(fields);
		   if (errors.size() > 0) {
			   throw new ApiRequestException(prepareErrorMessage(errors), HttpStatus.UNPROCESSABLE_ENTITY);
		   }
	   }
	   
	   private String prepareErrorMessage(List<ValidationErrorDTO> errors) {
		   StringBuilder errorMsg = new StringBuilder();
		   
		   
		   
		   return errorMsg.toString();
	   }

}
