package com.lockdown.account;

import java.time.LocalDate;

import com.lockdown.budget.BudgetEntry;
import com.lockdown.money.Money;

public class BudgetedTransaction extends Transaction {

	private final BudgetEntryMapping mapping;
	
	public BudgetedTransaction(String id, LocalDate date, String description, String party, Money amount, BudgetEntryMapping mapping) {
		super(id, date, description, party, amount);
		this.mapping = mapping;
	}
	
	public BudgetedTransaction() {
		this(null, LocalDate.now(), "Unnamed transaction", "Unknown", Money.zero(), SingleBudgetEntryMapping.none());
	}
	
	public static BudgetedTransaction now(String description, String party, Money amount, BudgetEntryMapping mapping) {
		return new BudgetedTransaction(null, LocalDate.now(), description, party, amount, mapping);
	}
	
	public static Transaction zero() {
		return new BudgetedTransaction();
	}

	public Money amountFor(BudgetEntry entry) {
		return mapping.amountFor(this, entry);
	}
	
	@Override
	public String toString() {
		return "Transaction [date=" + date + ", amount=" + amount + ", mapping=" + mapping + "]";
	}
}
