package com.lockdown.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lockdown.domain.Account;

public class AccountResource  extends ResourceSupport {

	private final String accountId;
	private final String name;
	private final String type;
	private final String subType;
	
	public AccountResource(Account account) {
		this.accountId = account.getId();
		this.name = account.getName();
		this.type = account.getType();
		this.subType = account.getSubtype();
	}

	@JsonProperty("id")
	public String getAccountId() {
		return accountId;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getSubType() {
		return subType;
	}
}
