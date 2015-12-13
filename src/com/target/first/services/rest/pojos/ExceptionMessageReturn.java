package com.target.first.services.rest.pojos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO - For parser to/from JAVA/JSON/JAVA
 * 
 * @author Klaus VIllaca
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionMessageReturn implements Serializable {

	private static final long serialVersionUID = 6162536639916318903L;
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("cause")
	private String cause;

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	@Override
	public String toString() {
		return "ExceptionMessageReturn [code=" + code + ", message=" + message + ", cause=" + cause + "]";
	}
	
	
}
