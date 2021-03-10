package com.siliconmtn.io.api.validation.validator;

// JDK 11.x
import java.util.ArrayList;
import java.util.List;

// Spacelibs
import com.siliconmtn.data.text.StringUtil;
import com.siliconmtn.io.api.validation.ValidationErrorDTO;
import com.siliconmtn.io.api.validation.ValidationErrorDTO.ValidationError;

/****************************************************************************
 * <b>Title</b>: AbstractValidator.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> Abstract validator that handles default behaviour for validators
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Eric Damschroder
 * @version 3.0
 * @since Mar 9, 2021
 * @updates:
 ****************************************************************************/

public abstract class AbstractValidator implements ValidatorIntfc {


	/**
	 * Default validation list that runs through all validation paths 
	 * for the validation DTO and returns all errors
	 */
	@Override
	public List<ValidationErrorDTO> validate(ValidationDTO validation) {
		List<ValidationErrorDTO> errors = new ArrayList<>();

		if (validation.getMin() != null) validateMin(validation, errors);
		if (validation.getMax() != null) validateMax(validation, errors);
		if (validation.getRegex() != null) validateRegex(validation, errors);
		if (validation.isRequired()) validateRequired(validation, errors);
		
		return errors;
	}


	/**
	 * Default isRequired validation. Covers the standard checks that there is some value present
	 * when the validation is set as being required.
	 */
	public void validateRequired(ValidationDTO validation, List<ValidationErrorDTO> errors) {
		if (validation.isRequired() && StringUtil.isEmpty(validation.getValue())) {
			errors.add(ValidationErrorDTO.builder().elementId(validation.getElementId()).value(validation).errorMessage("Value is required and nothing was set").validationError(ValidationError.REQUIRED).build());
		}
	}

	/**
	 * Empty validation checks to be overridden by the extending validators as needed.
	 * When the child does not extend them they are used to ensure that the checks are
	 * able to run without causing issues
	 */
	public void validateMin(ValidationDTO validation, List<ValidationErrorDTO> errors) { /* Empty default method */ }
	public void validateMax(ValidationDTO validation, List<ValidationErrorDTO> errors) { /* Empty default method */ }
	public void validateRegex(ValidationDTO validation, List<ValidationErrorDTO> errors) { /* Empty default method */ }
	
	
}
