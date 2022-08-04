package com.babl.smbspringcloudgateway;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.*;

/**
 * Common data setup to run tests.
 * 
 * @author Rashedul Islam
 * @since 2022-07-24
 */
public class ServiceData {

	public static String asJsonString(final Object obj) throws JsonProcessingException {
		return newObjectMapper().writeValueAsString(obj);
	}

	public static <T> T asObject(final String json, Class<T> type) throws JsonMappingException, JsonProcessingException {
		return newObjectMapper().readValue(json, type);
	}

	public static ObjectMapper newObjectMapper() {
		return new ObjectMapper()
				.setSerializationInclusion(JsonInclude.Include.NON_NULL)
				.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.configure(SerializationFeature.INDENT_OUTPUT, true)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.registerModule(new JavaTimeModule())
				.registerModule(new Jdk8Module());
	}

	public static class StringCollection extends ArrayList<String> {
		private static final long serialVersionUID = 1L;
	}

}
