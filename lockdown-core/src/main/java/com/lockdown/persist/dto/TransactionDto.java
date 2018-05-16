package com.lockdown.persist.dto;

import java.time.LocalDate;
import java.util.Optional;

import com.lockdown.domain.BudgetItemMapping;
import com.lockdown.domain.Money;
import com.lockdown.domain.Transaction;

public class TransactionDto extends Dto {
	
	private final LocalDate date;
	private final long amountInCents;
	private final String name;
	private final String description;
	private final BudgetItemMapping mapping;

	public TransactionDto(Transaction transaction) {
		super(transaction.getId());
		this.date = transaction.getDate();
		this.amountInCents = transaction.getAmount().asCents();
		this.name = transaction.getName();
		this.description = transaction.getDescription();
		this.mapping = transaction.getBudgetItemMapping().orElse(null);
	}
	
	public TransactionDto() {
		this.date = null;
		this.amountInCents = 0;
		this.name = null;
		this.description = null;
		this.mapping = null;
	}

	public Transaction toTransaction() {
		return new Transaction(getId(), date, Money.cents(amountInCents), name, description, Optional.ofNullable(mapping));
	}

	public LocalDate getDate() {
		return date;
	}

	public long getAmountInCents() {
		return amountInCents;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BudgetItemMapping getMapping() {
		return mapping;
	}
}
