package com.lockdown.persist.dto;

import com.lockdown.domain.Credentials;

public class CredentialsDto extends Dto {

	private final String itemId;
	private final String accessToken;
	
	public CredentialsDto(Credentials credentials) {
		super(credentials.getId());
		this.itemId = credentials.getItemId();
		this.accessToken = credentials.getAccessToken();
	}
	
	public CredentialsDto() {
		this.itemId = null;
		this.accessToken = null;
	}
	
	public Credentials toCredentials() {
		return new Credentials(getId(), itemId, accessToken);
	}

	public String getItemId() {
		return itemId;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
