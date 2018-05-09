package com.lockdown.account;

import java.util.Date;

import com.lockdown.budget.BudgetEntry;
import com.lockdown.money.DollarAmount;

public class Transaction {

	private final Date date;
	private final DollarAmount amount;
	private final BudgetEntryMapping mapping;
	
	public Transaction(Date date, DollarAmount amount, BudgetEntryMapping mapping) {
		this.date = date;
		this.amount = amount;
		this.mapping = mapping;
	}
	public static Transaction now(DollarAmount amount, BudgetEntryMapping mapping) {
		return new Transaction(new Date(), amount, mapping);
	}
	
	public static Transaction now(DollarAmount amount) {
		return now(amount, SingleBudgetEntryMapping.none());
	}
	
	public static Transaction zero() {
		return now(DollarAmount.zero());
	}

	public Date getDate() {
		return date;
	}

	public DollarAmount getAmount() {
		return amount;
	}
	
	public DollarAmount amountFor(BudgetEntry entry) {
		return mapping.amountFor(this, entry);
	}
	
	public boolean isExpense() {
		return amount.isNegative();
	}
	
	public boolean isDeposit() {
		return amount.isPositive();
	}
	@Override
	public String toString() {
		return "Transaction [date=" + date + ", amount=" + amount + ", mapping=" + mapping + "]";
	}
}
