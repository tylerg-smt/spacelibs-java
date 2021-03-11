package com.siliconmtn.io.api.validation.factory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/****************************************************************************
 * <b>Title</b>: StreamParser.java
 * <b>Project</b>: ezform-api
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Bala Gayatri Bugatha
 * @version 3.0
 * @since Mar 10, 2021
 * @updates:
 ****************************************************************************/

public abstract class StreamParser implements ParserIntfc{

	public List<Map<String, Object>> getMapList(byte[] ba) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(new String(ba, StandardCharsets.UTF_8), mapper.getTypeFactory().constructCollectionType(List.class, Map.class));

	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(byte[] ba) throws JsonProcessingException {

		return new ObjectMapper().readValue(new String(ba, StandardCharsets.UTF_8), Map.class);

	}

}
