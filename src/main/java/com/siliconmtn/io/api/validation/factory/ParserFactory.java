package com.siliconmtn.io.api.validation.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.siliconmtn.io.api.validation.validator.ValidationDTO;

/****************************************************************************
 * <b>Title</b>: ParserFactory.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Bala Gayatri Bugatha
 * @version 3.0
 * @since Mar 10, 2021
 * @updates:
 ****************************************************************************/
@Configuration
public class ParserFactory {
	
	@Value("#{${users}}") 
	private Map<String,ParserIntfc> builderMapper;
	public List<ValidationDTO> parserDispatcher(String controllerName,byte[] ba) throws SecurityException, IllegalArgumentException, JsonProcessingException {
	
//		String parserClassName=builderMapper.get(controllerName);
//		Class<?> c = Class.forName(parserClassName);
//		Object parseClassObject = c.newInstance(); 
//		Method setNameMethod =c.getClass().getMethod("requestParser", ByteArray.class);
//	    
//		setNameMethod.invoke(parseClassObject, ba);
		ParserIntfc factory = builderMapper.get(controllerName);
	    if (factory == null) {
	        return null;
	    }
	    return factory.requestParser(ba);
	}

}
