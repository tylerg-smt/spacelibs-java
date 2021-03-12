package com.siliconmtn.io.api.validation.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;

import com.siliconmtn.data.text.StringUtil;
import com.siliconmtn.io.api.ApiRequestException;

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
@PropertySource("classpath:application.properties")
public class ParserFactory {
	
	@Value("#{${parserMapper}}") 
	private Map<String,String> builderMapper;
	public ParserIntfc parserDispatcher(String controllerName) throws ApiRequestException {
		
		String parserClassName=builderMapper.get(controllerName);
		if (StringUtil.isEmpty(parserClassName)) return null;
		
		try {
			Class<?> c = Class.forName(parserClassName);
			return (ParserIntfc) c.getConstructor().newInstance(); 
		} catch (Exception e) {
			throw new ApiRequestException("Failed to create data parser", e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
