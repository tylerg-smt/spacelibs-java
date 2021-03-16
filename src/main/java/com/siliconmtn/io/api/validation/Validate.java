package com.siliconmtn.io.api.validation;

// Jdk 11.x
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.siliconmtn.io.api.validation.validator.UUIDValidator;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
/****************************************************************************
 * <b>Title</b>: Validate.java
 * <b>Project</b>: planit-api
 * <b>Description: </b> Annotation to be applied to an endpoint that needs to 
 * have its data validated.
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Mar 4, 2021
 * @updates:
 ****************************************************************************/
public @interface Validate {
	/**
	 * Types of data validations
	 */
	public enum ValidationType {
		NONE(null), 
		UUID(UUIDValidator.class), 
		FIELD(UUIDValidator.class), 
		FIELD_COLLECTION(UUIDValidator.class),
		CUSTOM(null);
		
		Class<?> validator;
		ValidationType(Class<?> validator) {
			this.validator = validator;
		}
		
		public Class<?> getValidator() { return this.validator; }
	}
	
	/**
	 * The validation type of the validate request
	 * @return The validation type
	 */
	public ValidationType validationType() default ValidationType.NONE;
}
