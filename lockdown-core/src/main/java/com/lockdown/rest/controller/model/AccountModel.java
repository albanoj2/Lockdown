package com.lockdown.rest.controller.model;

import com.lockdown.domain.Account;

public class AccountModel {

	private final Account account;
	private final String portfolioId;
	
	public AccountModel(Account account, String portfolioId) {
		this.account = account;
		this.portfolioId = portfolioId;
	}

	public Account getAccount() {
		return account;
	}
	
	public String getAccountId() {
		return account.getId();
	}

	public String getPortfolioId() {
		return portfolioId;
	}
}
