package com.lockdown.account;

import java.util.Date;

import com.lockdown.budget.BudgetEntry;
import com.lockdown.money.Money;

public class Transaction {

	private final Date date;
	private final Money amount;
	private final BudgetEntryMapping mapping;
	
	public Transaction(Date date, Money amount, BudgetEntryMapping mapping) {
		this.date = date;
		this.amount = amount;
		this.mapping = mapping;
	}
	public static Transaction now(Money amount, BudgetEntryMapping mapping) {
		return new Transaction(new Date(), amount, mapping);
	}
	
	public static Transaction now(Money amount) {
		return now(amount, SingleBudgetEntryMapping.none());
	}
	
	public static Transaction zero() {
		return now(Money.zero());
	}

	public Date getDate() {
		return date;
	}

	public Money getAmount() {
		return amount;
	}
	
	public Money amountFor(BudgetEntry entry) {
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
