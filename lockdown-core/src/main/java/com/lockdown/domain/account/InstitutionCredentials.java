package com.lockdown.domain.account;

import com.lockdown.domain.DomainObject;

public class InstitutionCredentials extends DomainObject {
	
	private final String itemId;
	private final String accessToken;
	
	public InstitutionCredentials(String id, String itemId, String accessToken) {
		super(id);
		this.itemId = itemId;
		this.accessToken = accessToken;
	}
	
	public InstitutionCredentials() {
		this(null, null, null);
	}

	public String getItemId() {
		return itemId;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
