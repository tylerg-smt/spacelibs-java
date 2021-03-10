package com.siliconmtn.io.api.validation;

// JDK 11.x
import java.lang.reflect.Constructor;

// Spacelibs
import com.siliconmtn.io.api.validation.validator.DefaultValidator;
import com.siliconmtn.io.api.validation.validator.ValidatorIntfc;
import com.siliconmtn.io.api.validation.validator.ValidatorIntfc.ValidatorType;

// Lombok 1.18.x
import lombok.extern.log4j.Log4j2;

/****************************************************************************
 * <b>Title</b>: ValidationFactory.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> Creates the validator for the supplied data type. If it cannot create
 * a proper validator it will create a validator that will always fail the data it is given.
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Eric Damschroder
 * @version 3.0
 * @since Mar 9, 2021
 * @updates:
 ****************************************************************************/

@Log4j2
public class ValidationFactory {

	
	/**
	 * Gets the validator of the enum type passed into the factory
	 * @param validator Enum of the validator type that should be built
	 * @return The desired validtor for the requested data type.
	 */
	public static ValidatorIntfc getValidator(ValidatorType validator) {
		try {
			Class<?> validatorObj = validator.getClass();
			Constructor<?> constructor = validatorObj.getDeclaredConstructor();
			return (ValidatorIntfc) constructor.newInstance();

		} catch (Exception e) {
			log.error("Can not load validator class " + e);
		}

		return new DefaultValidator();
	}
}
