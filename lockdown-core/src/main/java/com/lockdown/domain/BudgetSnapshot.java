package com.lockdown.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BudgetSnapshot {

	private final Budget budget;
	private final List<Account> accounts;
	private final Map<BudgetItem, BudgetItemSnapshot> budgetItemSnapshots;
	
	public BudgetSnapshot(Budget budget, List<Account> accounts) {
		this.budget = budget;
		this.accounts = accounts;
		this.budgetItemSnapshots = createBudgetItemSnapshotsMap();
	}
	
	private Map<BudgetItem, BudgetItemSnapshot> createBudgetItemSnapshotsMap() {
		
		List<Transaction> budgetedTransactions = getAllTransactions();
		Map<BudgetItem, BudgetItemSnapshot> snapshots = new HashMap<>();
		
		for (BudgetItem entry: budget.getItems()) {
			snapshots.put(entry, new BudgetItemSnapshot(entry, budgetedTransactions));
		}

		return snapshots;
	}
	
	public BudgetSnapshot() {
		this(Budget.empty(), new ArrayList<>());
	}
	
	public Map<BudgetItem, BudgetItemSnapshot> getBudgetItemSnapshotsMap() {
		return budgetItemSnapshots;
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
	
	public Money getTotalAccumulated() {
		return getBudgetItemSnapshots()
			.stream()
			.map(snapshot -> snapshot.getAccumulatedAmount())
			.reduce(Money::sum)
			.orElse(Money.zero());
	}
	
	private Collection<BudgetItemSnapshot> getBudgetItemSnapshots() {
		return budgetItemSnapshots.values();
	}
	
	public Money getTotalDeposited() {
		return getBudgetItemSnapshots()
			.stream()
			.map(snapshot -> snapshot.getDepositedAmount())
			.reduce(Money::sum)
			.orElse(Money.zero());
	}
	
	public Money getTotalExpensed() {
		return getBudgetItemSnapshots()
			.stream()
			.map(snapshot -> snapshot.getExpensedAmount())
			.reduce(Money::sum)
			.orElse(Money.zero());
	}
	
	public Money getTotalRemaining() {
		return getBudgetItemSnapshots()
			.stream()
			.map(snapshot -> snapshot.getRemainingAmount())
			.reduce(Money::sum)
			.orElse(Money.zero());
	}
	
	public Money getTotalValue() {
		return getTotalAccumulated().sum(getTotalDeposited());
	}
}