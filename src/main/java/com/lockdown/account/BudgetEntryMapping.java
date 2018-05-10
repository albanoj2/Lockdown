package com.lockdown.account;

import com.lockdown.budget.BudgetEntry;
import com.lockdown.money.Money;

@FunctionalInterface
public interface BudgetEntryMapping {
	public Money amountFor(Transaction transaction, BudgetEntry entry);
}
