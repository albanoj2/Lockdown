package com.lockdown.domain;

import java.time.LocalDate;

public class TransactionBody {

	private final LocalDate date;
	private final Money amount;
	private final String name;
	private final String description;
	private boolean isPending;
	
	public TransactionBody(LocalDate date, Money amount, String name, String description, boolean isPending) {
		this.date = date;
		this.amount = amount;
		this.name = name;
		this.description = description;
		this.isPending = isPending;
	}

	public boolean isPending() {
		return isPending;
	}

	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

	public LocalDate getDate() {
		return date;
	}

	public Money getAmount() {
		return amount;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
