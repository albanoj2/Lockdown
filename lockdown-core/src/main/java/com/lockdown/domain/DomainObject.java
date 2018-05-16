package com.lockdown.domain;

public abstract class DomainObject {

	private String id;
	
	protected DomainObject() {}
	
	protected DomainObject(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
