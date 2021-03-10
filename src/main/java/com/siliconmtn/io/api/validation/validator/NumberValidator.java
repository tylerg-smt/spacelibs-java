package com.siliconmtn.io.api.validation.validator;

// JDK 11.x
import java.util.List;

// Spacelibs
import com.siliconmtn.io.api.validation.ValidationErrorDTO;
import com.siliconmtn.io.api.validation.ValidationErrorDTO.ValidationError;
import com.siliconmtn.data.format.NumberUtil;

/****************************************************************************
 * <b>Title</b>: NumberValidator.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> Validator that handles validation of integer parameters.
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Eric Damschroder
 * @version 3.0
 * @since Mar 9, 2021
 * @updates:
 ****************************************************************************/

public class NumberValidator extends AbstractValidator {

	/**
	 * Ensure that the supplied number is not lower than the minimum
	 */
	@Override
	public void validateMin(ValidationDTO validation, List<ValidationErrorDTO> errors) {
		if (NumberUtil.toLong(validation.getValue()) < NumberUtil.toLong(validation.getMin())) {
			errors.add(new ValidationErrorDTO(validation.getElementId(), validation.getValue(), "Value is under the required minimum length of " + validation.getMin(), ValidationError.RANGE));
		}
	}

	/**
	 * Ensure that the supplied number is not higher than the maximum
	 */
	@Override
	public void validateMax(ValidationDTO validation, List<ValidationErrorDTO> errors) {
		if (NumberUtil.toLong(validation.getValue()) > NumberUtil.toLong(validation.getMax())) {
			errors.add(new ValidationErrorDTO(validation.getElementId(), validation.getValue(), "Value is over the required maximum length of " + validation.getMin(), ValidationError.RANGE));
		}
	}

}
