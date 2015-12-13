package com.target.first.services.rest.utils;

import javax.ws.rs.core.Response;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.first.persistence.enums.HTTPEnums;
import com.target.first.services.rest.pojos.ExceptionMessageReturn;

/**
 * Handles Exceptions to produce a JSON answer
 * 
 * @author Klaus Villaca
 *
 */
public class ExceptionsToJson {
	
	private static final String EXCEPTION_JSON_NAME = "ExceptionMessageReturn";
	private static final String HTTP_500 = "{\"" + EXCEPTION_JSON_NAME + "\": {\"code\":\"" + HTTPEnums.CODE_500.getCode() + "\", \"message\":\"" + HTTPEnums.CODE_500.getMessage() + "\"}}";
	
	/**
	 * Receive an exception plus http code and assemble a json string to return
	 * 
	 * @param exceptionToParse
	 * @param httpCode
	 * @return
	 */
	public static Response parseExceptionReceived(final Exception exceptionToParse, final String httpCode) {
		Response response = null;
		String exceptionAsJsonString = parseExceptionReceivedToString(exceptionToParse, httpCode);
		final String code = (httpCode != null)? httpCode : HTTPEnums.CODE_404.getCode();
		response = Response.status(Integer.parseInt(code)).entity(exceptionAsJsonString).build();
		return response;
	}

	
	/**
	 * parse the Exception adding the http code to a JSON string
	 * 
	 * @param exceptionToParse
	 * @param httpCode
	 * @return
	 */
	public static String parseExceptionReceivedToString(final Exception exceptionToParse, final String httpCode) {
		String exceptionAsJsonString = null;
		final ExceptionMessageReturn messageToReturn = new ExceptionMessageReturn();
		final String code = (httpCode != null)? httpCode : HTTPEnums.CODE_404.getCode();
		final String cause = (ExceptionUtils.getRootCauseMessage(exceptionToParse) != null)? ExceptionUtils.getRootCauseMessage(exceptionToParse) : null;
		final String message = (exceptionToParse.getMessage() != null)? exceptionToParse.getMessage() : HTTPEnums.CODE_404.getMessage();
		
		messageToReturn.setMessage(message);
		messageToReturn.setCause(cause);
		messageToReturn.setCode(code);
				
		// I could add a switch to make it more 'intelligent' and depending upon exception
		// produce a better response using more diversified HTTP response codes to reflect a better code - response
		
		try {
			final ObjectMapper mapper = new ObjectMapper();
			exceptionAsJsonString = mapper.writeValueAsString(messageToReturn);
		} catch (Exception e) {
			// It it fails we assembly something to return, that will be 500, Internal Server Error
			// I'm using a string to don't have to handle exceptions
			// Technically all messages should come from a properties file and also have a exception
			// factory to reach this method
			exceptionAsJsonString = HTTP_500;
		}
		return exceptionAsJsonString;
	}
	
}
