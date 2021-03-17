package com.siliconmtn.io.api.validation;

// JDK 11.x
import java.lang.reflect.Method;

// Spring 5.5.x
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

// Spacelibs 1.0
import com.siliconmtn.io.api.EndpointRequestException;

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
	   @Before("@annotation(com.siliconmtn.io.api.validation.Validate)")
	   public void aroundAdvice(JoinPoint jp) throws EndpointRequestException {
		   Method m = MethodSignature.class.cast(jp.getSignature()).getMethod();
		   Validate validate = m.getAnnotation(Validate.class);
		   
		   log.info("################## Validating Types: " + validate.validationType() + "|" + validate.validationType().getValidator());
	   } 

}
