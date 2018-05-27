package com.lockdown.rest.error;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6569880808030957576L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public static ResourceNotFoundException forResource(String resource, String id) {
		return new ResourceNotFoundException("Cannot find resource " + resource + " with ID " + id);
	}
	
	public static Supplier<ResourceNotFoundException> supplierForResource(String resource, String id) {
		return () -> forResource(resource, id);
	}
}
