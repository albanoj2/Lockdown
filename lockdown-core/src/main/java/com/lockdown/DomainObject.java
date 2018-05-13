package com.lockdown;

import org.springframework.data.annotation.Id;

public abstract class DomainObject {

	@Id
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
