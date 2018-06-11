package com.lockdown.rest.resource;

import java.util.Optional;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.lockdown.domain.BudgetItemMapping;
import com.lockdown.domain.Transaction;

@JsonInclude(Include.NON_NULL)
public class TransactionResource extends ResourceSupport {

	private final String transactionId;
	private final String date;
	private final long amount;
	private final String name;
	private final String description;
	private final boolean isPending; 
	private final String comment;
	@JsonUnwrapped
	private final BudgetItemMappingsResource budgetItemMappings;
	
	public TransactionResource(Transaction transaction) {
		this.transactionId = transaction.getId();
		this.date = transaction.getDate().toString();
		this.amount = transaction.getAmount().asCents();
		this.name = transaction.getName();
		this.description = transaction.getDescription();
		this.isPending = transaction.isPending();
		this.comment = transaction.getComment().orElse(null);
		this.budgetItemMappings = toResource(transaction.getBudgetItemMapping());
	}
	
	private static BudgetItemMappingsResource toResource(Optional<BudgetItemMapping> mapping) {
		
		if (mapping.isPresent()) {
			return new BudgetItemMappingsResource(mapping.get());
		}
		else {
			return null;
		}
	}

	@JsonProperty("id")
	public String getTransactionId() {
		return transactionId;
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
	
	public String getComment() {
		return comment;
	}

	protected BudgetItemMappingsResource getBudgetItemMappings() {
		return budgetItemMappings;
	}
}
