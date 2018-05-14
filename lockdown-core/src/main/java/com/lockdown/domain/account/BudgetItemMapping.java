package com.lockdown.domain.account;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;

@FunctionalInterface
public interface BudgetItemMapping {
	
	public Money amountFor(Transaction transaction, BudgetItem item);
	
	public default Money mappedAmount(Transaction transaction) {
		return transaction.getAmount();
	}
	
	public default boolean isValidFor(Transaction transaction) {
		return mappedAmount(transaction).equals(transaction.getAmount());
	}
}
