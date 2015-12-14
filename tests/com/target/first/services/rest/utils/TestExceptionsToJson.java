package com.target.first.services.rest.utils;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.target.first.persistence.enums.HTTPEnums;

public class TestExceptionsToJson {
	
	private ExceptionsToJson exceptionsToJson;
	
	@Before
	public void setUp() {
		exceptionsToJson = new ExceptionsToJson();
	}
	
	@After
	public void tearDown() {
		exceptionsToJson = null;
	}
	
	@Test
	public void testParseExceptionReceivedToString() {
		System.out.println("testParseExceptionReceivedToString");
		
		Response resp = exceptionsToJson.parseExceptionReceived(null, null);
		assertNotNull(resp);
		assertEquals("Expected return 404", 404, resp.getStatus());
	}
	
	@Test
	public void testParseExceptionReceivedToStringWithBothValues() throws JSONException {
		System.out.println("testParseExceptionReceivedToStringWithBothValues");
		Exception ex = new Exception("Dummy Exception");
		String resp = exceptionsToJson.parseExceptionReceivedToString(ex, HTTPEnums.CODE_404.getCode());
		assertNotNull(resp);
		
		JSONObject json = new JSONObject(resp);
		assertNotNull("Expected to have a json", json);
	}

	
	@Test
	public void testParseExceptionReceivedToStringWithBothValuesEmptyMesasge() throws JSONException {
		System.out.println("testParseExceptionReceivedToStringWithBothValues");
		Exception ex = new Exception();
		String resp = exceptionsToJson.parseExceptionReceivedToString(ex, HTTPEnums.CODE_404.getCode());
		assertNotNull(resp);
		
		JSONObject json = new JSONObject(resp);
		assertNotNull("Expected to have a json", json);
	}
	
}
