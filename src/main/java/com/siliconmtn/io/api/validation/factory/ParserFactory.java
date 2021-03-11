package com.siliconmtn.io.api.validation.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/****************************************************************************
 * <b>Title</b>: ParserFactory.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> Gives an instance of appropriate parser/packager
 * for a specific a aspect/controller call
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
	private Map<String,String> builderMapper;
	public ParserIntfc parserDispatcher(String controllerName) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException{
	
		String parserClassName=builderMapper.get(controllerName);
		Class<?> c = Class.forName(parserClassName);
		return (ParserIntfc) c.newInstance(); 
	}

}
