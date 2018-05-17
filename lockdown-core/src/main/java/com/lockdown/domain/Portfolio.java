package com.lockdown.domain;

import java.util.ArrayList;
import java.util.List;

public class Portfolio extends DomainObject {

	private final Budget budget;
	private final List<Account> accounts;
	private final List<Credentials> credentials;
	
	public Portfolio(String id, Budget budget, List<Account> accounts, List<Credentials> credentials) {
		super(id);
		this.budget = budget;
		this.accounts = accounts;
		this.credentials = credentials;
	}
	
	public Portfolio() {
		this.budget = Budget.empty();
		this.accounts = new ArrayList<>();
		this.credentials = new ArrayList<>();
	}

	public Budget getBudget() {
		return budget;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public List<Credentials> getCredentials() {
		return credentials;
	}
	
	public void addCredentials(Credentials newCredentials) {
		credentials.add(newCredentials);
	}
}
