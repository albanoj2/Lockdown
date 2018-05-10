package com.lockdown.portfolio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lockdown.account.Account;
import com.lockdown.account.BudgetedTransaction;
import com.lockdown.budget.Budget;
import com.lockdown.budget.BudgetEntry;

public class BudgetSnapshot {

	private Budget budget;
	private List<Account> accounts;
	
	public BudgetSnapshot(Budget budget, List<Account> accounts) {
		this.budget = budget;
		this.accounts = accounts;
	}
	
	public BudgetSnapshot() {
		this(Budget.empty(), new ArrayList<>());
	}
	
	public Map<BudgetEntry, BudgetEntrySnapshot> getBudgetEntrySnapshots() {
		
		List<BudgetedTransaction> budgetedTransactions = getAllTransactions();
		Map<BudgetEntry, BudgetEntrySnapshot> snapshots = new HashMap<>();
		
		for (BudgetEntry entry: budget.getEntries()) {
			snapshots.put(entry, new BudgetEntrySnapshot(entry, budgetedTransactions));
		}

		return snapshots;
	}
	
	private List<BudgetedTransaction> getAllTransactions() {
		return accounts.stream()
			.flatMap(account -> account.getBudgetedTransactions().stream())
			.collect(Collectors.toList());
	}
}