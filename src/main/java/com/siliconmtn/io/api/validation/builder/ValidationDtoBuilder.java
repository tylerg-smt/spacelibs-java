package com.siliconmtn.io.api.validation.builder;

import com.siliconmtn.io.api.validation.validator.ValidationDTO;
import com.siliconmtn.io.api.validation.validator.ValidatorIntfc.ValidatorType;
/****************************************************************************
 * <b>Title</b>: ValidationDTOBuilder.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Bala Gayatri Bugatha
 * @version 3.0
 * @since Mar 10, 2021
 * @updates:
 ****************************************************************************/
public class ValidationDtoBuilder{
	
	private String elementId;
	private String value;
	private String min;
	private String max;
	private String regex;
	private boolean isRequired;
	private ValidatorType type;
	
	public ValidationDtoBuilder(String elementId, String value, String min, String max, String regex, boolean isRequired, ValidatorType type) {
		this.elementId=elementId;
		this.value=value;
		this.min=min;
		this.max=max;
		this.regex=regex;
		this.isRequired=isRequired;
		this.type=type;
	}
	
	/**
	 * @param elementId the elementId to set
	 */
	public ValidationDtoBuilder setElementId(String elementId) {
		this.elementId = elementId;
		return this;
	}

	/**
	 * @param value the value to set
	 */
	public ValidationDtoBuilder setValue(String value) {
		this.value = value;
		return this;
	}

	/**
	 * @param min the min to set
	 */
	public ValidationDtoBuilder setMin(String min) {
		this.min = min;
		return this;
	}

	/**
	 * @param max the max to set
	 */
	public ValidationDtoBuilder setMax(String max) {
		this.max = max;
		return this;
	}
	
	/**
	 * @param regex the regex to set
	 */
	public ValidationDtoBuilder setRegex(String regex) {
		this.regex = regex;
		return this;
	}

	/**
	 * @param isRequired the isRequired to set
	 */
	public ValidationDtoBuilder setRequired(boolean isRequired) {
		this.isRequired = isRequired;
		return this;
	}

	/**
	 * @param type the type to set
	 */
	public ValidationDtoBuilder setType(ValidatorType type) {
		this.type = type;
		return this;
	}

	
	public ValidationDTO convertToValidationDTO(Object o) {
		return new ValidationDTO(elementId,value,min,max,regex,isRequired,type);
	}
	
	

}