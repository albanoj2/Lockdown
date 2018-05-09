package com.lockdown.account;

import java.util.Objects;

import com.lockdown.budget.BudgetEntry;
import com.lockdown.money.DollarAmount;

public class SingleBudgetEntryMapping implements BudgetEntryMapping {

	private final BudgetEntry entry;
	
	public SingleBudgetEntryMapping(BudgetEntry entry) {
		this.entry = Objects.requireNonNull(entry);
	}
	
	public static SingleBudgetEntryMapping none() {
		return new SingleBudgetEntryMapping(BudgetEntry.none());
	}

	@Override
	public DollarAmount amountFor(Transaction transaction, BudgetEntry entry) {
		
		Objects.requireNonNull(entry);
		
		if (this.entry.equals(entry)) {
			return transaction.getAmount();
		}
		else {
			return DollarAmount.zero();
		}
	}

	@Override
	public String toString() {
		return "Budget entry mapping to " + entry;
	}
}
