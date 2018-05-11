package com.lockdown.persist;

public class DomainObjectNotFoundException extends PersistenceException {

	private static final long serialVersionUID = -8477899569915192765L;

	public DomainObjectNotFoundException(String message) {
		super(message);
	}
	
	public DomainObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DomainObjectNotFoundException forObject(long id, String type) {
		return new DomainObjectNotFoundException("Cannot find " + type + " with ID " + id);
	}
}
