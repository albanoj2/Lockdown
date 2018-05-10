package com.lockdown.account;

import java.time.LocalDate;

import com.lockdown.money.Money;

public class UnbudgetedTransaction extends Transaction {

	public UnbudgetedTransaction(long id, LocalDate date, String description, String party, Money amount) {
		super(id, date, description, party, amount);
	}
	
	public static UnbudgetedTransaction now(String description, String party, Money amount) {
		return new UnbudgetedTransaction(0, LocalDate.now(), description, party, amount);
	}

	public BudgetedTransaction budget(BudgetEntryMapping mapping) {
		return new BudgetedTransaction(getId(), getDate(), getDescription(), getParty(), getAmount(), mapping);
	}
}
