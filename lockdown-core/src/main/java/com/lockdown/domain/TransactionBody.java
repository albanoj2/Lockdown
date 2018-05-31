package com.lockdown.domain;

import java.time.LocalDate;
import java.util.Objects;

public class TransactionBody {

	private final LocalDate date;
	private final Money amount;
	private final String name;
	private final String description;
	private final boolean isPending;
	
	public TransactionBody(LocalDate date, Money amount, String name, String description, boolean isPending) {
		this.date = date;
		this.amount = amount;
		this.name = name;
		this.description = description;
		this.isPending = isPending;
	}
	
	public static TransactionBody empty() {
		return new TransactionBody(LocalDate.now(), Money.zero(), "Unnamed", "No description", false);
	}

	public boolean isPending() {
		return isPending;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (isPending ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) {
			return true;
		}
		else if (!(obj instanceof TransactionBody)) {
			return false;
		}
		else {
			TransactionBody other = (TransactionBody) obj;
			return Objects.equals(date, other.date) &&
				Objects.equals(amount, other.amount) &&
				Objects.equals(description, other.description) &&
				Objects.equals(isPending, other.isPending) &&
				Objects.equals(name, other.name);
		}
	}
}
