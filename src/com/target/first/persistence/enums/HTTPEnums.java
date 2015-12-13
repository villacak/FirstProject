package com.target.first.persistence.enums;

//
// Enum for get code and message, I would prefer have messages from properties file
//
public enum HTTPEnums {
	CODE_404("404", "NOT FOUND"), CODE_500("500", "INTERNAL SERVER ERROR"), CODE_200("200", "OK"), CODE_417("417", "EXPECTATION FAILED");
	
	private String code;
	private String message;
	
	private HTTPEnums(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}
