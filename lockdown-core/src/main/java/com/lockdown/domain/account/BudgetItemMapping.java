package com.lockdown.domain.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;

@Entity
@Inheritance
public abstract class BudgetItemMapping {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private final Long id;
	
	protected BudgetItemMapping(Long id) {
		this.id = id;
	}
	
	public abstract Money amountFor(Transaction transaction, BudgetItem item);
	
	public Money mappedAmount(Transaction transaction) {
		return transaction.getAmount();
	}
	
	public boolean isValidFor(Transaction transaction) {
		return mappedAmount(transaction).equals(transaction.getAmount());
	}

	public Long getId() {
		return id;
	}
}
