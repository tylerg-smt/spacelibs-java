package com.siliconmtn.io.api.validation;

// JDK 11.x
import java.io.Serializable;

// Lombok 1.18.x
import lombok.Builder;
import lombok.Data;

/****************************************************************************
 * <b>Title</b>: ValidationErrorDTO.java
 * <b>Project</b>: planit-api
 * <b>Description: </b> DTO to hold the error response for any fields that 
 * failed validation
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Mar 4, 2021
 * @updates:
 ****************************************************************************/
@Data
@Builder
public class ValidationErrorDTO implements Serializable {
	/**
	 * Enum containing the type of validation failed on the field
	 */
	public enum ValidationType {
		REQUIRED, REGEX, RANGE, OPTION
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Primary key of the data element that failed validation
	 */
	private String elementId;
	
	/**
	 * Value of the failed validation
	 */
	private Object value;
	
	/**
	 * Validation error message
	 */
	private String errorMessage; 
	
	/**
	 * The type of validation that failed
	 */
	private ValidationType validationType;

}
