package com.lockdown.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BudgetSnapshot {

	private final Budget budget;
	private final List<Account> accounts;
	
	public BudgetSnapshot(Budget budget, List<Account> accounts) {
		this.budget = budget;
		this.accounts = accounts;
	}
	
	public BudgetSnapshot() {
		this(Budget.empty(), new ArrayList<>());
	}
	
	public Map<BudgetItem, BudgetItemSnapshot> getBudgetEntrySnapshots() {
		
		List<Transaction> budgetedTransactions = getAllTransactions();
		Map<BudgetItem, BudgetItemSnapshot> snapshots = new HashMap<>();
		
		for (BudgetItem entry: budget.getItems()) {
			snapshots.put(entry, new BudgetItemSnapshot(entry, budgetedTransactions));
		}

		return snapshots;
	}
	
	private List<Transaction> getAllTransactions() {
		return accounts.stream()
			.flatMap(account -> account.getBudgetedTransactions().stream())
			.collect(Collectors.toList());
	}

	public Budget getBudget() {
		return budget;
	}

	public List<Account> getAccounts() {
		return accounts;
	}
}