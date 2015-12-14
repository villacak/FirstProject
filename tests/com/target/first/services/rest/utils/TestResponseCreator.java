package com.target.first.services.rest.utils;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.first.services.rest.pojos.ExceptionMessageReturn;

public class TestResponseCreator {
	
	
	private String jsonException;
	
	@Before
	public void setUp() throws JsonProcessingException {
		final ExceptionMessageReturn messageToReturn = new ExceptionMessageReturn();
		messageToReturn.setMessage("Returned Null");
		messageToReturn.setCause("NullPointerException");
		messageToReturn.setCode("404");
		final ObjectMapper mapper = new ObjectMapper();
		jsonException = mapper.writeValueAsString(messageToReturn);
	}

	@After
	public void tearDown() {
		jsonException = null;
	}

	@Test
	public void  testWrapResponseWithRightCodeSuccessNoException() {
		System.out.println("testWrapResponseWithRightCodeSuccessNoException");
		
		final String dummyJSON = "{\"id\":\"300\", \"productDescription\": \"Product number 1\"}";
		ResponseCreator responseCreator = new ResponseCreator(); 
		Response resp = responseCreator.wrapResponseWithRightCode(dummyJSON);
		assertNotNull("Expected to receive a response", resp);
		assertEquals("Expected to received an 200 Ok in the response", 200, resp.getStatus());
	}
	
	@Test
	public void  testWrapResponseWithRightCodeSuccessWithException() {
		System.out.println("testWrapResponseWithRightCodeSuccessWithException");
		
		ResponseCreator responseCreator = new ResponseCreator(); 
		Response resp = responseCreator.wrapResponseWithRightCode(jsonException);
		assertNotNull("Expected to receive a response", resp);
		assertEquals("Expected to received an 404 error in the response", 404, resp.getStatus());
	}
	
	@Test
	public void  testWrapResponseWithRightCodeEmpty() {
		System.out.println("testWrapResponseWithRightCodeEmpty");
		
		ResponseCreator responseCreator = new ResponseCreator(); 
		Response resp = responseCreator.wrapResponseWithRightCode("");
		assertNotNull("Expected to receive a response", resp);
		assertEquals("Expected to received an 404 error in the response", 404, resp.getStatus());
	}
	
	@Test
	public void  testWrapResponseWithRightCodeNull() {
		System.out.println("testWrapResponseWithRightCodeNull");
		
		ResponseCreator responseCreator = new ResponseCreator(); 
		Response resp = responseCreator.wrapResponseWithRightCode(null);
		assertNotNull("Expected to receive a response", resp);
		assertEquals("Expected to received an 404 error in the response", 404, resp.getStatus());
	}
}
