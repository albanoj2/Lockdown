package com.lockdown.account;

import com.lockdown.budget.BudgetEntry;
import com.lockdown.money.DollarAmount;

@FunctionalInterface
public interface BudgetEntryMapping {
	public DollarAmount amountFor(Transaction transaction, BudgetEntry entry);
}
