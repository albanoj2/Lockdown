package com.lockdown.domain.account;

import com.lockdown.domain.budget.BudgetEntry;
import com.lockdown.domain.money.Money;

@FunctionalInterface
public interface BudgetEntryMapping {
	public Money amountFor(Transaction budgetedTransaction, BudgetEntry entry);
}
