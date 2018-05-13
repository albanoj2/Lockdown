package com.lockdown.account;

import java.time.LocalDate;

import com.lockdown.DomainObject;
import com.lockdown.money.Money;

public abstract class Transaction extends DomainObject {

	protected final LocalDate date;
	protected final String description;
	protected final String party;
	protected final Money amount;

	protected Transaction(String id, LocalDate date, String description, String party, Money amount) {
		super(id);
		this.date = date;
		this.description = description;
		this.party = party;
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public String getParty() {
		return party;
	}

	public Money getAmount() {
		return amount;
	}

	public boolean isExpense() {
		return amount.isNegative();
	}

	public boolean isDeposit() {
		return amount.isPositive();
	}
}
