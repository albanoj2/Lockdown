package com.lockdown.domain.portfolio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lockdown.domain.account.Account;
import com.lockdown.domain.account.Transaction;
import com.lockdown.domain.budget.Budget;
import com.lockdown.domain.budget.BudgetItem;

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
	
	public Map<BudgetItem, BudgetItemSnapshot> getBudgetEntrySnapshots() {
		
		List<Transaction> budgetedTransactions = getAllTransactions();
		Map<BudgetItem, BudgetItemSnapshot> snapshots = new HashMap<>();
		
		for (BudgetItem entry: budget.getEntries()) {
			snapshots.put(entry, new BudgetItemSnapshot(entry, budgetedTransactions));
		}

		return snapshots;
	}
	
	private List<Transaction> getAllTransactions() {
		return accounts.stream()
			.flatMap(account -> account.getBudgetedTransactions().stream())
			.collect(Collectors.toList());
	}
}