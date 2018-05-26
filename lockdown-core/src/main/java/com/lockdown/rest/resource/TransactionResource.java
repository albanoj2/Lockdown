package com.lockdown.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lockdown.domain.Transaction;

@JsonInclude(Include.NON_NULL)
public class TransactionResource extends ResourceSupport {

	private final String date;
	private final long amount;
	private final String name;
	private final String description;
	private final boolean isPending; 
	
	public TransactionResource(Transaction transaction) {
		this.date = transaction.getDate().toString();
		this.amount = transaction.getAmount().asCents();
		this.name = transaction.getName();
		this.description = transaction.getDescription();
		this.isPending = transaction.isPending();
	}

	public String getDate() {
		return date;
	}

	public long getAmount() {
		return amount;
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
}
