package com.lockdown.portfolio;

import java.util.ArrayList;
import java.util.List;

import com.lockdown.DomainObject;
import com.lockdown.account.Account;
import com.lockdown.budget.Budget;

public class Portfolio extends DomainObject {

	@SuppressWarnings("unused")
	private Budget budget;
	
	@SuppressWarnings("unused")
	private List<Account> accounts;
	
	public Portfolio(String id, Budget budget, List<Account> accounts) {
		super(id);
		this.budget = budget;
		this.accounts = accounts;
	}
	
	public Portfolio() {
		this.budget = Budget.empty();
		this.accounts = new ArrayList<>();
	}
	
}
