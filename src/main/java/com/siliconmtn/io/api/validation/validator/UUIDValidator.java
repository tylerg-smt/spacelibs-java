package com.siliconmtn.io.api.validation.validator;

// JDK 11.x
import java.util.List;
import java.util.regex.Pattern;

// Spacelibs
import com.siliconmtn.data.text.StringUtil;
import com.siliconmtn.io.api.validation.ValidationErrorDTO;
import com.siliconmtn.io.api.validation.ValidationErrorDTO.ValidationError;

// Lombok 1.18.x
import lombok.NoArgsConstructor;

/****************************************************************************
 * <b>Title</b>: StringValidator.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> Validator that handles validation of String parameters.
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Eric Damschroder
 * @version 3.0
 * @since Mar 9, 2021
 * @updates:
 ****************************************************************************/

@NoArgsConstructor
public class UUIDValidator extends StringValidator {

	/**
	 * Ensure that the supplied string's length is not shorter than the minimum required length
	 */
	@Override
	public void validateMin(ValidationDTO validation, List<ValidationErrorDTO> errors) {
		if (validation.getMin() == null) {
			validation.setMin("36");
		}
		super.validateMin(validation, errors);
	}

	/**
	 * Ensure that the supplied string's length is not larger than the maximum required length
	 */
	@Override
	public void validateMax(ValidationDTO validation, List<ValidationErrorDTO> errors) {
		if (validation.getMax() == null) {
			validation.setMax("36");
		}
		super.validateMax(validation, errors);
	}

	/**
	 * Ensure that the supplied string matches the supplied regex pattern
	 */
	@Override
	public void validateRegex(ValidationDTO validation, List<ValidationErrorDTO> errors) {
		if (validation.getRegex() == null) {
			validation.setRegex("[A-za-z0-9]*-[A-za-z0-9]*-[A-za-z0-9]*-[A-za-z0-9]*-[A-za-z0-9]*");
		}
		super.validateRegex(validation, errors);
	}

}
