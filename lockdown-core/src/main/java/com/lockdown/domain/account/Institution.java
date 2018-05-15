package com.lockdown.domain.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lockdown.domain.DomainObject;

public class Institution extends DomainObject {

	private final String name;
	@JsonIgnore private final InstitutionCredentials credentials;
	private final List<Account> accounts;
	
	public Institution(String id, String name, InstitutionCredentials credentials, List<Account> accounts) {
		super(id);
		this.name = name;
		this.credentials = credentials;
		this.accounts = accounts;
	}

	public String getName() {
		return name;
	}

	public InstitutionCredentials getCredentials() {
		return credentials;
	}

	public List<Account> getAccounts() {
		return accounts;
	}
}
