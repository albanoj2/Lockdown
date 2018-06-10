package com.lockdown.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BudgetItemSnapshot {

	private final BudgetItem entry;
	private final List<Transaction> budgetedTransactions;
	
	public BudgetItemSnapshot(BudgetItem entry, List<Transaction> budgetedTransactions) {
		this.entry = entry;
		this.budgetedTransactions = budgetedTransactions;
	}
	
	public static BudgetItemSnapshot withoutTransactions(BudgetItem item) {
		return new BudgetItemSnapshot(item, new ArrayList<>());
	}
	
	public Money getExpensedAmount() {
		return sumTransactionAmountsIf(Transaction::isExpense).abs();
	}
	
	private Money sumTransactionAmountsIf(Predicate<Transaction> predicate) {
		return budgetedTransactions.stream()
			.filter(predicate)
			.map(transaction -> transaction.amountFor(entry))
			.reduce((amount1, amount2) -> amount1.sum(amount2))
			.orElse(Money.zero());
	}
	
	public Money getDepositedAmount() {
		return sumTransactionAmountsIf(Transaction::isDeposit);
	}
	
	public Money getBalance() {
		return getDepositedAmount().subtract(getExpensedAmount());
	}
	
	public Money getAccumulatedAmount() {
		return entry.getTotalAccumulatedAmount();
	}
	
	public Money getTotalValue() {
		return getAccumulatedAmount().sum(getDepositedAmount());
	}
	
	public Money getRemainingAmount() {
		return getBalance().sum(getAccumulatedAmount());
	}

	public BudgetItem getEntry() {
		return entry;
	}

	public List<Transaction> getTransactions() {
		return budgetedTransactions;
	}
}
