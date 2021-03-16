package com.siliconmtn.io.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.siliconmtn.io.api.validation.ValidationErrorDTO;
import com.siliconmtn.io.api.validation.ValidationErrorDTO.ValidationError;

public class ApiRequestExceptionTest {

	/**
	 * Test the constructor that accepts a list of validation errors
	 * @throws Exception
	 */
	@Test
	public void testApiRequestExceptionErrorConsturctor() throws Exception {
		List<ValidationErrorDTO> errors = new ArrayList<>();
		errors.add(ValidationErrorDTO.builder()
				.elementId("Id")
				.value("42")
				.validationError(ValidationError.RANGE)
				.build());
		errors.add(ValidationErrorDTO.builder()
				.elementId("name")
				.value("Douglas Adams")
				.validationError(ValidationError.REGEX)
				.build());
		
		ApiRequestException ex = new ApiRequestException("Failure", HttpStatus.ALREADY_REPORTED, errors);
		
		assert(ex.getMessage().equals("Failure"));
		assert(ex.getStatus().equals(HttpStatus.ALREADY_REPORTED));
		assert(ex.getFailedValidations().size() == 2);
		assert(ex.getFailedValidations().get(1).getValue().equals("Douglas Adams"));
		assert(ex.getFailedValidations().get(0).getValidationError().equals(ValidationError.RANGE));
		
	}

	/**
	 * Test the cause and status constructor along with the addFailedValidations methods
	 * @throws Exception
	 */
	@Test
	public void testApiRequestExceptionCuaseStatusConsturctor() throws Exception {

		ApiRequestException ex = new ApiRequestException(new Throwable("Failed to do the thing"), HttpStatus.CONFLICT);
		
		assert(ex.getCause().getMessage().equals("Failed to do the thing"));
		assert(ex.getStatus().equals(HttpStatus.CONFLICT));
		assert(ex.getFailedValidations().size() == 0);
		
		ex.addFailedValidation(ValidationErrorDTO.builder()
				.elementId("book")
				.value("Vogon Poetry")
				.validationError(ValidationError.CATASTROPHE)
				.build());

		assert(ex.getFailedValidations().size() == 1);
		assert(ex.getFailedValidations().get(0).getValidationError().equals(ValidationError.CATASTROPHE));
		
		List<ValidationErrorDTO> errors = new ArrayList<>();
		errors.add(ValidationErrorDTO.builder()
				.elementId("Id")
				.value("42")
				.validationError(ValidationError.RANGE)
				.build());
		errors.add(ValidationErrorDTO.builder()
				.elementId("name")
				.value("Douglas Adams")
				.validationError(ValidationError.REGEX)
				.build());

		ex.addAllFailedValidation(errors);
		
		assert(ex.getFailedValidations().size() == 3);
		assert(ex.getFailedValidations().get(1).getValue().equals("42"));
		assert(ex.getFailedValidations().get(2).getElementId().equals("name"));
	}

}
