package com.lockdown.rest;

public enum LinkRel {
	
	PORTFOLIO("portfolio"),
	ACCOUNT("account"),
	ACCOUNTS("account"),
	TRANSACTION("transaction"),
	TRANSACTIONS("transactions");

	private final String rel;
	
	private LinkRel(String rel) {
		this.rel = rel;
	}
	
	public String getRel() {
		return rel;
	}
}
