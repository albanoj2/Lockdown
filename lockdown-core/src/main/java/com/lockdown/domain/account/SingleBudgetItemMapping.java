package com.lockdown.domain.account;

import java.util.Objects;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;

public class SingleBudgetItemMapping implements BudgetItemMapping {

	private final BudgetItem to;
	
	public SingleBudgetItemMapping(BudgetItem entry) {
		this.to = Objects.requireNonNull(entry);
	}
	
	public static SingleBudgetItemMapping none() {
		return new SingleBudgetItemMapping(BudgetItem.blank());
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
