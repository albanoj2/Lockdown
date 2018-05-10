package com.lockdown;

import org.springframework.data.annotation.Id;

public abstract class DomainObject {

	@Id
	private long id;
	
	protected DomainObject() {}
	
	protected DomainObject(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
