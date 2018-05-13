package com.lockdown.domain.account;

import java.time.LocalDate;

import com.lockdown.domain.money.Money;

public class UnbudgetedTransaction extends Transaction {

	public UnbudgetedTransaction(String id, LocalDate date, String description, String party, Money amount) {
		super(id, date, description, party, amount);
	}
	
	public static UnbudgetedTransaction now(String description, String party, Money amount) {
		return new UnbudgetedTransaction(null, LocalDate.now(), description, party, amount);
	}

	public BudgetedTransaction budget(BudgetEntryMapping mapping) {
		return new BudgetedTransaction(getId(), getDate(), getDescription(), getParty(), getAmount(), mapping);
	}
}
