package com.siliconmtn.io.api.validation.validator;

// Spacelibs
import com.siliconmtn.io.api.validation.validator.ValidatorIntfc.ValidatorType;

// Lombok 1.18.x
import lombok.AllArgsConstructor;
import lombok.Data;

/****************************************************************************
 * <b>Title</b>: ValidationDTO.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> DTO containing all information needed to validation a single peice of information.
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Eric Damschroder
 * @version 3.0
 * @since Mar 9, 2021
 * @updates:
 ****************************************************************************/

@Data
@AllArgsConstructor
public class ValidationDTO {

	private String elementId;
	private String value;
	private String min;
	private String max;
	private String regex;
	private boolean isRequired;
	private ValidatorType type;

}
