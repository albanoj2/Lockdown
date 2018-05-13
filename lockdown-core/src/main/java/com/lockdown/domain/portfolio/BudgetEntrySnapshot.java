package com.lockdown.domain.portfolio;

import java.util.List;
import java.util.function.Predicate;

import com.lockdown.domain.account.BudgetedTransaction;
import com.lockdown.domain.budget.BudgetEntry;
import com.lockdown.domain.money.Money;

public class BudgetEntrySnapshot {

	private final BudgetEntry entry;
	private final List<BudgetedTransaction> budgetedTransactions;
	
	public BudgetEntrySnapshot(BudgetEntry entry, List<BudgetedTransaction> budgetedTransactions) {
		this.entry = entry;
		this.budgetedTransactions = budgetedTransactions;
	}
	
	public Money getExpensedAmount() {
		return sumTransactionAmountsIf(BudgetedTransaction::isExpense).abs();
	}
	
	private Money sumTransactionAmountsIf(Predicate<BudgetedTransaction> predicate) {
		return budgetedTransactions.stream()
			.filter(predicate)
			.map(transaction -> transaction.amountFor(entry))
			.reduce((amount1, amount2) -> amount1.sum(amount2))
			.orElse(Money.zero());
	}
	
	public Money getDepositedAmount() {
		return sumTransactionAmountsIf(BudgetedTransaction::isDeposit);
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

	public List<BudgetedTransaction> getTransactions() {
		return budgetedTransactions;
	}
}
