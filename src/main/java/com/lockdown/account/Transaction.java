package com.lockdown.account;

import java.util.Date;

public class Transaction {

	private final Date date;
	private final DollarAmount amount;
	
	public Transaction(Date date, DollarAmount amount) {
		this.date = date;
		this.amount = amount;
	}
	
	public static Transaction now(DollarAmount amount) {
		return new Transaction(new Date(), amount);
	}

	public Date getDate() {
		return date;
	}

	public DollarAmount getAmount() {
		return amount;
	}
}
