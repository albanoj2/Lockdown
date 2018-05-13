package com.lockdown.rest.controller.error;

import java.util.function.Supplier;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6056590911717386804L;

	public ResourceNotFoundException(long id) {
		super("Cannot find desired resource with ID " + id);
	}
	
	public static Supplier<RuntimeException> forId(long id) {
		return () -> new ResourceNotFoundException(id);
	}
}
