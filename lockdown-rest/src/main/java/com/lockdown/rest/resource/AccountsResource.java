package com.lockdown.rest.resource;

import java.util.List;
import java.util.stream.Collectors;

import com.lockdown.account.Account;

public class AccountsResource {

	private final List<AccountResource> accounts;
	
	private AccountsResource(List<AccountResource> accounts) {
		this.accounts = accounts;
	}
	
	public static AccountsResource from(List<Account> accounts) {
		List<AccountResource> resources = accounts.stream()
			.map(account -> AccountResource.from(account))
			.collect(Collectors.toList());
		return new AccountsResource(resources);
	}

	public List<AccountResource> getAccounts() {
		return accounts;
	}
}
