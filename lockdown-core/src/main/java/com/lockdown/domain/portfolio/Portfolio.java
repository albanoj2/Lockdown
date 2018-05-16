package com.lockdown.domain.portfolio;

import java.util.ArrayList;
import java.util.List;

import com.lockdown.domain.DomainObject;
import com.lockdown.domain.account.Account;
import com.lockdown.domain.account.Credentials;
import com.lockdown.domain.budget.Budget;

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
}
