package com.lockdown.rest.controller.error;

public class InvalidResourceSuppliedException extends RuntimeException {

	private static final long serialVersionUID = -332597141641022568L;

	public InvalidResourceSuppliedException(String message) {
		super("Invalid resource supplied: " + message);
	}
}
