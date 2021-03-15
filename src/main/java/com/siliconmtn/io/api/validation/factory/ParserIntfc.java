package com.siliconmtn.io.api.validation.factory;
import java.io.IOException;
import java.util.List;

import com.siliconmtn.io.api.validation.validator.ValidationDTO;
/****************************************************************************
 * <b>Title</b>: RequestBodyParser.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> Interface for parsing request body to list of validation dtos
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Bala Gayatri Bugatha
 * @version 3.0
 * @since Mar 10, 2021
 * @updates:
 ****************************************************************************/
public interface ParserIntfc {
	
	List<ValidationDTO> requestParser(Object ba) throws JsonProcessingException;

}
