package com.target.first.services.rest.utils;

import javax.json.JsonException;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import com.target.first.persistence.enums.HTTPEnums;

/**
 * Response creator if ok or exception
 * 
 * @author Klaus Villaca
 *
 */
public class ResponseCreator {
	
	private static final String MESSAGE = "message"; 
	private static final String CODE = "code";
	
	public ResponseCreator() {
		super();
	}
	
	
	
	/**
	 * Receive a response that may be a good or bad data wrap in the appropriated response
	 * 
	 * @param preReadyResponse
	 * @return
	 */
	public Response wrapResponseWithRightCode(final String preReadyResponse) {
		Response responseToReturn = null;
		if (preReadyResponse == null || preReadyResponse.trim().length() == 0) {
			final ExceptionsToJson exceptionToJson = new ExceptionsToJson();
			responseToReturn = exceptionToJson.parseExceptionReceived(null,HTTPEnums.CODE_404.getCode());
		} else if (preReadyResponse != null && preReadyResponse.contains(CODE) && preReadyResponse.contains(MESSAGE)) {
			try {
				final JSONObject tempJson = new JSONObject(preReadyResponse);
				final String code = tempJson.getString(CODE);
				responseToReturn = Response.status(Integer.parseInt(code)).entity(preReadyResponse).build();
			} catch (Exception e) {
				final ExceptionsToJson exceptionToJson = new ExceptionsToJson();
				responseToReturn = exceptionToJson.parseExceptionReceived(e, HTTPEnums.CODE_417.getCode());
			}
		} else {
			try {
				// Just to validate the json
				@SuppressWarnings("unused")
				final JSONObject tempJson = new JSONObject(preReadyResponse);
				responseToReturn = Response.ok(preReadyResponse).build();
			} catch (Exception je) {
				final ExceptionsToJson exceptionToJson = new ExceptionsToJson();
				responseToReturn = exceptionToJson.parseExceptionReceived(je, HTTPEnums.CODE_417.getCode());
			}
			
		}
		return responseToReturn;
	}
}
