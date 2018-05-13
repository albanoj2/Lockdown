package com.lockdown.rest.controller.error;

public class InvalidAccountException extends InvalidResourceException {
	
	private static final long serialVersionUID = 6034733350971012488L;

	public InvalidAccountException(String message) {
		super("Invalid account: " + message);
	}
}
