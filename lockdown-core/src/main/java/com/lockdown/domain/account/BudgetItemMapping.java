package com.lockdown.domain.account;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;

@FunctionalInterface
public interface BudgetItemMapping {
	public Money amountFor(Transaction transaction, BudgetItem entry);
}
