package com.lockdown;

public abstract class DomainObject {

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
