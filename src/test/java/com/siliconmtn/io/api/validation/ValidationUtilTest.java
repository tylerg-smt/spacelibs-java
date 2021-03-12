package com.siliconmtn.io.api.validation;

// JDK 11.x
import java.util.ArrayList;
import java.util.List;

// Junit 5
import org.junit.jupiter.api.Test;

// Spacelibs
import com.siliconmtn.io.api.validation.ValidationErrorDTO.ValidationError;
import com.siliconmtn.io.api.validation.validator.ValidationDTO;
import com.siliconmtn.io.api.validation.validator.ValidatorIntfc.ValidatorType;

/****************************************************************************
 * <b>Title</b>: ValidationUtilTest.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> Test class that handles the validation utility and all associated
 * validators, DTO's, and factories.
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Eric Damschroder
 * @version 3.0
 * @since Mar 9, 2021
 * @updates:
 ****************************************************************************/

public class ValidationUtilTest {


	/**
	 * Test the String validator, testing it's ability to fail based on min, max, isRequired, and regex.
	 */
	@Test
	public void testStringValidators() {
		List<ValidationDTO> fields = new ArrayList<>(7);

		// Successful min, max, regex, and required test
		fields.add(new ValidationDTO("id", "value", "1", "100", "va", true, ValidatorType.STRING));
		// Fails due to minimum
		fields.add(new ValidationDTO("id", "value", "8", null, null, false, ValidatorType.STRING));
		// Fails due to maximum
		fields.add(new ValidationDTO("id", "value", null, "2", null, false, ValidatorType.STRING));
		// Fails due to regex
		fields.add(new ValidationDTO("id", "value", null, null, "pie", false, ValidatorType.STRING));
		// Fails due to required
		fields.add(new ValidationDTO("id", null, null, null, null, true, ValidatorType.STRING));
		// Fails due to required
		fields.add(new ValidationDTO("id", "", null, null, null, true, ValidatorType.STRING));
		// Successful validation
		fields.add(new ValidationDTO("id", null, null, null, null, false, ValidatorType.STRING));
		
		List<ValidationErrorDTO> errors = ValidationUtil.validateData(fields);
		
		assert(errors.size() == 5);
		assert(errors.get(0).getValidationError().equals(ValidationError.RANGE));
		assert(errors.get(1).getValidationError().equals(ValidationError.RANGE));
		assert(errors.get(2).getValidationError().equals(ValidationError.REGEX));
		assert(errors.get(3).getValidationError().equals(ValidationError.REQUIRED));
		assert(errors.get(4).getValidationError().equals(ValidationError.REQUIRED));
		
	}
	
	
	/**
	 * Test the email validator, testing it's ability to fail based on regex 
	 * as the other methods are handled by its parent calss of StringValidator.
	 */
	@Test
	public void testEmailValidators() {
		List<ValidationDTO> fields = new ArrayList<>(7);

		// Successful regex test
		fields.add(ValidationDTO.builder().elementId("id").value("edamschroder@siliconmtn.com").type(ValidatorType.EMAIL).build());
		fields.add(ValidationDTO.builder().elementId("id").value("fakeemail@somerandomemaildomain.uk").type(ValidatorType.EMAIL).build());
		// Fails due to regex
		fields.add(ValidationDTO.builder().elementId("id").value("edamsc@hroder@siliconmtn.com").type(ValidatorType.EMAIL).build());
		fields.add(ValidationDTO.builder().elementId("id").value("edamschrodersiliconmtn.com").type(ValidatorType.EMAIL).build());
		fields.add(ValidationDTO.builder().elementId("id").value("edamsc@hroder@siliconmtncom").type(ValidatorType.EMAIL).build());
		fields.add(ValidationDTO.builder().elementId("id").value("edamsc@hroder@siliconmtn.").type(ValidatorType.EMAIL).build());
		
		List<ValidationErrorDTO> errors = ValidationUtil.validateData(fields);
		
		assert(errors.size() == 4);
		assert(errors.get(0).getValidationError().equals(ValidationError.REGEX));
		assert(errors.get(1).getValidationError().equals(ValidationError.REGEX));
		assert(errors.get(2).getValidationError().equals(ValidationError.REGEX));
		assert(errors.get(3).getValidationError().equals(ValidationError.REGEX));
		
	}
	
	
	/**
	 * Test the email validator, testing it's ability to fail based on regex 
	 * as the other methods are handled by its parent calss of StringValidator.
	 */
	@Test
	public void testUUIDValidators() {
		List<ValidationDTO> fields = new ArrayList<>(6);

		// Successful regex test
		fields.add(ValidationDTO.builder().elementId("id").value("fjhq-p1d84-fjqA2239-fjqa3334-fq343f9").type(ValidatorType.UUID).build());
		fields.add(ValidationDTO.builder().elementId("id").value("FD98av-shef-AN526UF-hfaqsdf-adfdJf26").type(ValidatorType.UUID).build());
		// Fails due to regex
		fields.add(ValidationDTO.builder().elementId("id").value("FD9-8a!shef-ANc56UF-hf3asdf-adfJaaa6").type(ValidatorType.UUID).build());
		fields.add(ValidationDTO.builder().elementId("id").value("FD98-icshef-AN5d6UF-hfa2sdfadfJ22ac6").type(ValidatorType.UUID).build());
		// Fails due to min length
		fields.add(ValidationDTO.builder().elementId("id").value("FD98-aisef-A56UF-hfadf-adfJ2").type(ValidatorType.UUID).build());
		// Fails due to max length
		fields.add(ValidationDTO.builder().elementId("id").value("FD98aiA-Sf3shef-AN5d6adsf43UF-hfads3shdf-adfafad3J26").type(ValidatorType.UUID).build());
		
		List<ValidationErrorDTO> errors = ValidationUtil.validateData(fields);
		for (ValidationErrorDTO e : errors) System.out.println(e);
		assert(errors.size() == 4);
		assert(errors.get(0).getValidationError().equals(ValidationError.REGEX));
		assert(errors.get(1).getValidationError().equals(ValidationError.REGEX));
		assert(errors.get(2).getValidationError().equals(ValidationError.RANGE));
		assert(errors.get(3).getValidationError().equals(ValidationError.RANGE));
		
	}


	/**
	 * Test the date validator, testing it's ability to fail based on min, max, isRequired, 
	 * and the inability to parse any supplied date to be tested, either by the user or by the system
	 */
	@Test
	public void testDateValidators() {
		List<ValidationDTO> fields = new ArrayList<>(7);

		// Successful min, max, and required test, ignoring the regex despite one being passed
		fields.add(new ValidationDTO("id", "10/10/2020", "10/9/2020", "10/11/2020", "[A-Z]", true, ValidatorType.DATE));
		//Fail due to parse min and parse max
		fields.add(new ValidationDTO("id", "9-8-2020", "December 12, 2020", "Octember 12, 2020", null, false, ValidatorType.DATE));
		// Fails due to parse value
		fields.add(new ValidationDTO("id", "Jul 17, 2021", "12/20/2020", "12/20/2020", null, false, ValidatorType.DATE));
		// Fail due to not meeting min
		fields.add(new ValidationDTO("id", "9-8-2020", "12/20/2020", null, null, false, ValidatorType.DATE));
		// Fail due to not meeting min
		fields.add(new ValidationDTO("id", "6/1/2030", null, "1/13/1920", null, false, ValidatorType.DATE));
		// Fail due to required
		fields.add(new ValidationDTO("id", null, null, null, null, true, ValidatorType.DATE));
		// Successful Test
		fields.add(new ValidationDTO("id", null, "12/20/2020", "12/20/2020", null, false, ValidatorType.DATE));
		
		List<ValidationErrorDTO> errors = ValidationUtil.validateData(fields);
		
		assert(errors.size() == 6);
		assert(errors.get(0).getValidationError().equals(ValidationError.PARSE));
		assert(errors.get(1).getValidationError().equals(ValidationError.PARSE));
		assert(errors.get(2).getValidationError().equals(ValidationError.PARSE));
		assert(errors.get(3).getValidationError().equals(ValidationError.RANGE));
		assert(errors.get(4).getValidationError().equals(ValidationError.RANGE));
		assert(errors.get(5).getValidationError().equals(ValidationError.REQUIRED));
		
	}


	/**
	 * Test the date validator, testing it's ability to fail based on min, max, isRequired, 
	 * and the inability to parse any supplied date to be tested, either by the user or by the system
	 */
	@Test
	public void testDefaultValidator() {
		List<ValidationDTO> fields = new ArrayList<>(1);

		// Fails due to lack of type
		fields.add(new ValidationDTO("id", "Test", null, null, null, false, null));

		
		List<ValidationErrorDTO> errors = ValidationUtil.validateData(fields);
		
		assert(errors.size() == 1);
		assert(errors.get(0).getValidationError().equals(ValidationError.CATASTROPHE));
		
	}


	/**
	 * Test the date validator, testing it's ability to fail based on min, max, isRequired, 
	 * and the inability to parse any supplied date to be tested, either by the user or by the system
	 */
	@Test
	public void testNumberValidator() {
		List<ValidationDTO> fields = new ArrayList<>(7);

		// Succeeds min, max and requierd test, ignoring the regex despite one being passed
		fields.add(new ValidationDTO("id", "5", "1", "7", "pointless", true, ValidatorType.NUMBER));
		// Fails due to not providing a proper number
		fields.add(new ValidationDTO("id", "1234Five", "1", "7", null, false, ValidatorType.NUMBER));
		// Succeeds with decimal number
		fields.add(new ValidationDTO("id", "5.0", "1", "7", null, true, ValidatorType.NUMBER));
		// Fails due to min
		fields.add(new ValidationDTO("id", "8", "20", null, null, false, ValidatorType.NUMBER));
		// Fails due to max
		fields.add(new ValidationDTO("id", "4", null, "2", null, false, ValidatorType.NUMBER));
		//Fails due to required
		fields.add(new ValidationDTO("id", null, null, null, null, true, ValidatorType.NUMBER));
		// Succeeds null value
		fields.add(new ValidationDTO("id", null, null, null, null, false, ValidatorType.NUMBER));
		
		List<ValidationErrorDTO> errors = ValidationUtil.validateData(fields);
		
		assert(errors.size() == 4);
		assert(errors.get(0).getValidationError().equals(ValidationError.PARSE));
		assert(errors.get(1).getValidationError().equals(ValidationError.RANGE));
		assert(errors.get(2).getValidationError().equals(ValidationError.RANGE));
		assert(errors.get(3).getValidationError().equals(ValidationError.REQUIRED));
		
	}
	
	/**
	 * Checks to ensure that an empty list of validatables returns properly.
	 */
	@Test
	public void testNoValidation() {
		List<ValidationErrorDTO> errors = ValidationUtil.validateData(null);
		
		assert(errors.size() == 0);
	}
	
	/**
	 * Checks that objects can be properly instantiated, even when all they have
	 * is a single static method.
	 */
	@Test
	public void testInstantiation() {
		ValidationUtil util = new ValidationUtil();
		assert(util != null && util.getClass() == ValidationUtil.class);
		
		ValidationFactory factory = new ValidationFactory();
		assert(factory != null && factory.getClass() == ValidationFactory.class);
	}
}
