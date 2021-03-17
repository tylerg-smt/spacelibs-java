package com.siliconmtn.io.api.validation.validator;

// JDK 11.x
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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
		
		if (validation.isRequired()) validateRequired(validation, errors);
		
		if (validation.getValidOptions() != null && !validation.isAlternateValidationId()) {
			validateOptions(validation, errors);
			
			// If we are validating against options this is all that needs to be done for validation
			return errors;
		}
		
		if (validation.getMin() != null) validateMin(validation, errors);
		if (validation.getMax() != null) validateMax(validation, errors);
		if (validation.getRegex() != null) validateRegex(validation, errors);
		
		return errors;
	}
	
	/**
	 * Determine whether the value is in the list of accepted values.
	 * @param validation
	 * @param errors
	 * @return true to show that validation is complete and nothing else needs done, false to show that further validation is needed.
	 */
	public void validateOptions(ValidationDTO validation, List<ValidationErrorDTO> errors) {
		
		for (Entry<String, String> e : validation.getValidOptions().entrySet()) {
			// Value is in map, validation complete and successful.
			if (e.getValue() == null || e.getValue().equals(validation.getValue())) return;
		}
		
		errors.add(ValidationErrorDTO.builder()
				.elementId(validation.getElementId())
				.value(validation.getValue())
				.errorMessage("Value is not in the supplied list of accepted values")
				.validationError(ValidationError.OPTION)
				.build());
	}


	/**
	 * Default isRequired validation. Covers the standard checks that there is some value present
	 * when the validation is set as being required.
	 */
	public void validateRequired(ValidationDTO validation, List<ValidationErrorDTO> errors) {
		if (validation.isRequired() && StringUtil.isEmpty(validation.getValue())) {
			errors.add(ValidationErrorDTO.builder().elementId(validation.getElementId()).value(validation.getValue()).errorMessage("Value is required and nothing was set").validationError(ValidationError.REQUIRED).build());
		}
	}

	/**
	 * Empty validation checks for regex as both numbers and dates do not use it, allowing them to not have to implement them.
	 */
	public void validateRegex(ValidationDTO validation, List<ValidationErrorDTO> errors) { /* Empty default method */ }
	
	
}
