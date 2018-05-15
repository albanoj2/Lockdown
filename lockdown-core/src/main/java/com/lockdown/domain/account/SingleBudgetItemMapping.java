package com.lockdown.domain.account;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;

@Entity
public class SingleBudgetItemMapping extends BudgetItemMapping {

	@ManyToOne
	private final BudgetItem to;
	
	public SingleBudgetItemMapping(Long id, BudgetItem entry) {
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
