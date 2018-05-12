package com.lockdown.rest.resource;

import java.util.List;

import com.lockdown.account.Account;
import com.lockdown.account.BudgetedTransaction;
import com.lockdown.account.UnbudgetedTransaction;

public class AccountResource {

	private final long id;
	private final List<UnbudgetedTransaction> unbudgetedTransactions;
	private final List<BudgetedTransaction> budgetedTransactions;
	
	public AccountResource(Account account) {
		this.id = account.getId();
		this.unbudgetedTransactions = account.getUnbudgetedTransactions();
		this.budgetedTransactions = account.getBudgetedTransactions();
	}

	public long getId() {
		return id;
	}

	public List<UnbudgetedTransaction> getUnbudgetedTransactions() {
		return unbudgetedTransactions;
	}

	public List<BudgetedTransaction> getBudgetedTransactions() {
		return budgetedTransactions;
	}
}
