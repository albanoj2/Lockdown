package com.lockdown.rest.controller.error;

public class ErrorResponseBody {

	private final String message;
	
	public ErrorResponseBody(RuntimeException exception) {
		this(exception.getMessage());
	}
	
	public ErrorResponseBody(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
