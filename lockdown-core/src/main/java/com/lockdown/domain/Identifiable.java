package com.lockdown.domain;

public abstract class Identifiable {

	private String id;
	
	protected Identifiable() {}
	
	protected Identifiable(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
