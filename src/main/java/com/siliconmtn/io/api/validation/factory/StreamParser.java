package com.siliconmtn.io.api.validation.factory;
import java.util.Map;
/****************************************************************************
 * <b>Title</b>: StreamParser.java
 * <b>Project</b>: ezform-api
 * <b>Description: </b> Abstract class implementing ParserIntfc 
 * to change request body to map of string and object
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Bala Gayatri Bugatha
 * @version 3.0
 * @since Mar 10, 2021
 * @updates:
 ****************************************************************************/

public abstract class StreamParser implements ParserIntfc{

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(Object ba) {
		return (Map<String, Object>)ba;
	}

}
