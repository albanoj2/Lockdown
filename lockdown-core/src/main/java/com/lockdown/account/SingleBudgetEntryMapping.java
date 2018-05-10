package com.lockdown.account;

import java.util.Objects;

import com.lockdown.budget.BudgetEntry;
import com.lockdown.money.Money;

public class SingleBudgetEntryMapping implements BudgetEntryMapping {

	private final BudgetEntry entry;
	
	public SingleBudgetEntryMapping(BudgetEntry entry) {
		this.entry = Objects.requireNonNull(entry);
	}
	
	public static SingleBudgetEntryMapping none() {
		return new SingleBudgetEntryMapping(BudgetEntry.blank());
	}

	@Override
	public Money amountFor(Transaction budgetedTransaction, BudgetEntry entry) {
		
		Objects.requireNonNull(entry);
		
		if (this.entry.equals(entry)) {
			return budgetedTransaction.getAmount();
		}
		else {
			return Money.zero();
		}
	}

	@Override
	public String toString() {
		return "Budget entry mapping to " + entry;
	}
}
