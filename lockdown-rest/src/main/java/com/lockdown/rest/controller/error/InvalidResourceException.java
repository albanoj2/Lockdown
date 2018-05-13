package com.lockdown.rest.controller.error;

public class InvalidResourceException extends RuntimeException {

	private static final long serialVersionUID = -332597141641022568L;

	public InvalidResourceException(String message) {
		super(message);
	}
}
