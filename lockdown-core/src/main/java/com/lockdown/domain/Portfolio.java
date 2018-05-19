package com.lockdown.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Portfolio extends Identifiable {

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
	
	public void removeCredentials(Credentials credentialsToRemove) {
		credentials.remove(credentialsToRemove);
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
	}
	
	public void removeAccount(Account account) {
		accounts.remove(account);
	}
	
	public boolean addAccountIfNotExists(Account account) {
		
		if (!hasAccountWithKey(account.getKey())) {
			addAccount(account);
			return true;
		}
		
		return false;
	}
	
	public boolean hasAccountWithKey(String key) {
		return getAccountWithKey(key)
			.isPresent();
	}
	
	public Optional<Account> getAccountWithKey(String key) {
		return accounts.stream()
			.filter(a -> a.getKey().equals(key))
			.findFirst();
	}
}
