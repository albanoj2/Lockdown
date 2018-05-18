package com.lockdown.service.sync.provider;

public class DiscoveredAccount {

	private final String key;
	private final String name;
	private final String type;
	private final String subtype;
	
	public DiscoveredAccount(String key, String name, String type, String subtype) {
		this.key = key;
		this.name = name;
		this.type = type;
		this.subtype = subtype;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getSubtype() {
		return subtype;
	}
}
