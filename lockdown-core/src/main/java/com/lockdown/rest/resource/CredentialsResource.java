package com.lockdown.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lockdown.domain.Credentials;

public class CredentialsResource extends ResourceSupport {

	private final String credentialsId;
	private final String itemId;
	private final String accessToken;
	
	public CredentialsResource(Credentials credentials) {
		this.credentialsId = credentials.getId();
		this.itemId = credentials.getItemId();
		this.accessToken = credentials.getAccessToken();
	}

	@JsonProperty("id")
	public String getCredentialsId() {
		return credentialsId;
	}

	public String getItemId() {
		return itemId;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
