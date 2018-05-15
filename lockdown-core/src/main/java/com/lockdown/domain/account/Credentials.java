package com.lockdown.domain.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Credentials {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private final Long id;
	private final String itemId;
	private final String accessToken;
	
	public Credentials(Long id, String itemId, String accessToken) {
		this.id = id;
		this.itemId = itemId;
		this.accessToken = accessToken;
	}
	
	public Credentials() {
		this(null, null, null);
	}

	public Long getId() {
		return id;
	}

	public String getItemId() {
		return itemId;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
