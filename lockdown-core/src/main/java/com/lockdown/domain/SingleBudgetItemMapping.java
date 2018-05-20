package com.lockdown.domain;

import java.util.Objects;

public class SingleBudgetItemMapping extends Identifable implements BudgetItemMapping {

	private final BudgetItem to;
	
	public SingleBudgetItemMapping(String id, BudgetItem entry) {
		super(id);
		this.to = Objects.requireNonNull(entry);
	}
	
	public static SingleBudgetItemMapping none() {
		return new SingleBudgetItemMapping(null, BudgetItem.blank());
	}

	@Override
	public Money amountFor(Transaction btransaction, BudgetItem entry) {
		
		Objects.requireNonNull(entry);
		
		if (this.to.equals(entry)) {
			return btransaction.getAmount();
		}
		else {
			return Money.zero();
		}
	}

	@Override
	public String toString() {
		return "Budget entry mapping to " + to;
	}
}
