package com.lockdown.rest.resource;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lockdown.domain.BudgetItem;

@JsonInclude(Include.NON_NULL)
public class BudgetItemResource extends ResourceSupport {

	private final String budgetItemId;
	private final String name;
	private final String description;
	private final long amountPerFrequency;
	private final String frequency;
	private final String start;
	private final String end;
	private final boolean isActive;
	
	public BudgetItemResource(BudgetItem item) {
		this.budgetItemId = item.getId();
		this.name = item.getName();
		this.description = item.getDescription();
		this.amountPerFrequency = item.getAmountPerFrequency().asCents();
		this.frequency = item.getFrequency().name();
		this.start = item.getStart().toString();
		this.end = formatDate(item.getEnd());
		this.isActive = item.isActive();
	}
	
	private static String formatDate(Optional<LocalDate> date) {
		return date.isPresent() ? date.get().toString() : null;
	}

	@JsonProperty("id")
	public String getBudgetItemId() {
		return budgetItemId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public long getAmountPerFrequency() {
		return amountPerFrequency;
	}

	public String getFrequency() {
		return frequency;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public boolean isActive() {
		return isActive;
	}
}
