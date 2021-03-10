package com.siliconmtn.io.api.validation.dtobuilder;
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
public class ValidationDTOBuilder{
	
	private String elementId;
	private String value;
	private String min;
	private String max;
	private String regex;
	private boolean isRequired;
	private ValidatorType type;
	
	public ValidationDTOBuilder(String elementId, String value, String min, String max, String regex, boolean isRequired, ValidatorType type) {
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
	public ValidationDTOBuilder setElementId(String elementId) {
		this.elementId = elementId;
		return this;
	}

	/**
	 * @param value the value to set
	 */
	public ValidationDTOBuilder setValue(String value) {
		this.value = value;
		return this;
	}

	/**
	 * @param min the min to set
	 */
	public ValidationDTOBuilder setMin(String min) {
		this.min = min;
		return this;
	}

	/**
	 * @param max the max to set
	 */
	public ValidationDTOBuilder setMax(String max) {
		this.max = max;
		return this;
	}
	
	/**
	 * @param regex the regex to set
	 */
	public ValidationDTOBuilder setRegex(String regex) {
		this.regex = regex;
		return this;
	}

	/**
	 * @param isRequired the isRequired to set
	 */
	public ValidationDTOBuilder setRequired(boolean isRequired) {
		this.isRequired = isRequired;
		return this;
	}

	/**
	 * @param type the type to set
	 */
	public ValidationDTOBuilder setType(ValidatorType type) {
		this.type = type;
		return this;
	}

	@Override
	public ValidationDTO convertToValidationDTO(Object o) {
		// TODO Auto-generated method stub
		return new ValidationDTO(elementId,value,min,max,regex,isRequired,type);
	}
	
	

}
