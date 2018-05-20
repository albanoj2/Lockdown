package com.lockdown.domain;

public class Credentials extends Identifable {
	
	private final String itemId;
	private final String accessToken;
	
	public Credentials(String id, String itemId, String accessToken) {
		super(id);
		this.itemId = itemId;
		this.accessToken = accessToken;
	}
	
	public Credentials() {
		this(null, null, null);
	}

	public String getItemId() {
		return itemId;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
