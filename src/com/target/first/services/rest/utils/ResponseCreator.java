package com.target.first.services.rest.utils;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.target.first.persistence.enums.HTTPEnums;

public class ResponseCreator {
	
	private static final String ERROR_JSON_OBJECT = "ExceptionMessageReturn";
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
			responseToReturn = ExceptionsToJson.parseExceptionReceived(null,HTTPEnums.CODE_404.getCode());
		} else if (preReadyResponse != null && preReadyResponse.contains(ERROR_JSON_OBJECT)) {
			try {
				final JSONObject tempJson = new JSONObject(preReadyResponse);
				final JSONObject errorValues = tempJson.getJSONObject(ERROR_JSON_OBJECT);
				final String code = errorValues.getString(CODE);
				responseToReturn = Response.status(Integer.parseInt(code)).entity(preReadyResponse).build();
			} catch (JSONException je) {
				responseToReturn = ExceptionsToJson.parseExceptionReceived(je, HTTPEnums.CODE_417.getCode());
			}
		} else {
			responseToReturn = Response.ok(preReadyResponse).build();
		}
		return responseToReturn;
	}
}
