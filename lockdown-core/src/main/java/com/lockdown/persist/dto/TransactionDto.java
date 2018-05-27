package com.lockdown.persist.dto;

import java.time.LocalDate;
import java.util.Optional;

import com.lockdown.domain.BudgetItemMapping;
import com.lockdown.domain.Money;
import com.lockdown.domain.Transaction;

public class TransactionDto extends Dto {
	
	private final LocalDate date;
	private final long amountInCents;
	private final String key;
	private final String name;
	private final String description;
	private final boolean isPending;
	private final String comment;
	private final BudgetItemMapping mapping;

	public TransactionDto(Transaction transaction) {
		super(transaction.getId());
		this.date = transaction.getDate();
		this.amountInCents = transaction.getAmount().asCents();
		this.key = transaction.getKey();
		this.name = transaction.getName();
		this.description = transaction.getDescription();
		this.isPending = transaction.isPending();
		this.comment = transaction.getComment().orElse(null);
		this.mapping = transaction.getBudgetItemMapping().orElse(null);
	}
	
	public TransactionDto() {
		this.date = null;
		this.amountInCents = 0;
		this.key = null;
		this.name = null;
		this.description = null;
		this.isPending = false;
		this.comment = null;
		this.mapping = null;
	}

	public Transaction toTransaction() {
		return new Transaction(getId(), date, Money.cents(amountInCents), key, name, description, isPending, Optional.ofNullable(comment), Optional.ofNullable(mapping));
	}

	public LocalDate getDate() {
		return date;
	}

	public long getAmountInCents() {
		return amountInCents;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isPending() {
		return isPending;
	}

	public BudgetItemMapping getMapping() {
		return mapping;
	}
}
