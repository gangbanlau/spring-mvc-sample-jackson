package com.com.com.util;

import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.com.com.domain.User;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class JsonObjectConverterTest {

	private static final Logger logger = LoggerFactory.getLogger(JsonObjectConverterTest.class);
	
	static int LOOP_COUNT = 1000000;

	@Test
	public void testPOJO2JSON_OptionalField() throws IOException {
		Date now = new Date();
		User u = new User();
		u.setId(1L);
		u.setName("spring");
		u.setSomeTime(now);
		u.setAnotherTime(now);
		u.setPassword("pwd");
		//u.setOptionalval(1);
		
		String json = JsonObjectConverter.convertToJsonString(u);
		Assert.assertEquals(-1, json.indexOf("optionalval"));
		logger.info(json);
	}
	
	@Test
	public void testJSON2POJO_OptionalField() throws IOException {
		String json = "{\"id\":1,\"name\":\"spring\",\"someTime\":1466650619386,\"anotherTime\":\"2016-06-23 10:56:59\",\"Password\":\"pwd\", \"UNKNOWNFIELD\": \"value\"}";
		
		User u = (User)JsonObjectConverter.convertToObject(json, User.class);
		Assert.assertNull("", u.getOptionalval());
	}
	
	@Test	// (expected=UnrecognizedPropertyException.class)	@JsonIgnoreProperties(ignoreUnknown=true)
	public void testJSON2POJO_UnknownField() throws IOException {
		String json = "{\"id\":1,\"name\":\"spring\",\"someTime\":1466650619386,\"anotherTime\":\"2016-06-23 10:56:59\",\"Password\":\"pwd\", \"UNKNOWNFIELD\": \"value\"}";
		
		User u = (User)JsonObjectConverter.convertToObject(json, User.class);
		Assert.assertNull("", u.getOptionalval());
	}
	
}
