package com.siliconmtn.io.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.siliconmtn.io.api.validation.ValidationErrorDTO;
import com.siliconmtn.io.api.validation.ValidationErrorDTO.ValidationError;

/****************************************************************************
 * <b>Title</b>: ApiResponseTest.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> Build out tests to check any stragler constructors 
 * and currently unused methods in the ApiResponse 
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Eric Damschroder
 * @version 3.0
 * @since Mar 16, 2021
 * @updates:
 ****************************************************************************/

public class ApiResponseTest {
	
	
	/**
	 * Test the data only constructor as well as the addFailedValidation method
	 * @throws Exception
	 */
	@Test
	public void testApiReponseDataConstructor() throws Exception {
		ApiResponse dataConstructor = new ApiResponse("Test");
		
		assert(dataConstructor.getFailedValidations().size() == 0);
		
		dataConstructor.addFailedValidation(ValidationErrorDTO.builder()
				.elementId("Id")
				.value("42")
				.validationError(ValidationError.REGEX)
				.build());
		
		assert(dataConstructor.data.equals("Test"));
		assert(dataConstructor.getCount() == 0);
		assert(dataConstructor.getStatus().equals(HttpStatus.OK));
		assert(dataConstructor.isSuccess());
		assert(dataConstructor.getFailedValidations().size() == 1);
		assert(dataConstructor.getFailedValidations().get(0).getValidationError().equals(ValidationError.REGEX));
		
	}
	
	
	/**
	 * Test the data and count constructor
	 * @throws Exception
	 */
	@Test
	public void testApiReponseDataCountConstructor() throws Exception {
		ApiResponse dataConstructor = new ApiResponse("Test", 5);
		assert(dataConstructor.data.equals("Test"));
		assert(dataConstructor.getCount() == 5);
		assert(dataConstructor.getStatus().equals(HttpStatus.OK));
		assert(dataConstructor.isSuccess());
	}

}
