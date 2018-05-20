package com.lockdown.domain;

public abstract class Identifable {

	private String id;
	
	protected Identifable() {}
	
	protected Identifable(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
