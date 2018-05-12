package com.lockdown.rest.resource;

import java.util.List;
import java.util.stream.Collectors;

import com.lockdown.account.Account;

public class AccountResource {

	private final long id;
	private final String name;
//	private final List<UnbudgetedTransaction> unbudgetedTransactions;
//	private final List<BudgetedTransaction> budgetedTransactions;
	
	private AccountResource(Account account) {
		this.id = account.getId();
		this.name = account.getName();
//		this.unbudgetedTransactions = account.getUnbudgetedTransactions();
//		this.budgetedTransactions = account.getBudgetedTransactions();
	}
	
	public static AccountResource from(Account account) {
		return new AccountResource(account);
	}
	
	public static List<AccountResource> forEach(List<Account> accounts) {
		return accounts.stream()
			.map(account -> AccountResource.from(account))
			.collect(Collectors.toList());
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

//	public List<UnbudgetedTransaction> getUnbudgetedTransactions() {
//		return unbudgetedTransactions;
//	}
//
//	public List<BudgetedTransaction> getBudgetedTransactions() {
//		return budgetedTransactions;
//	}
	
	
}
