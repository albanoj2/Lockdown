package com.lockdown.portfolio;

import java.util.List;
import java.util.function.Predicate;

import com.lockdown.account.Transaction;
import com.lockdown.budget.BudgetEntry;
import com.lockdown.money.Money;

public class BudgetEntrySnapshot {

	private final BudgetEntry entry;
	private final List<Transaction> transactions;
	
	public BudgetEntrySnapshot(BudgetEntry entry, List<Transaction> transactions) {
		this.entry = entry;
		this.transactions = transactions;
	}
	
	public Money getExpensedAmount() {
		return sumTransactionAmountsIf(Transaction::isExpense).abs();
	}
	
	private Money sumTransactionAmountsIf(Predicate<Transaction> predicate) {
		return transactions.stream()
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
	
	public Money getRemainingAmount() {
		return getBalance().sum(getAccumulatedAmount());
	}

	public BudgetEntry getEntry() {
		return entry;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}
}
