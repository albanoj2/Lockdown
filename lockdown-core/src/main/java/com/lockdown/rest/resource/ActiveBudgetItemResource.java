package com.lockdown.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lockdown.domain.BudgetItem;

public class ActiveBudgetItemResource extends ResourceSupport {

	private final String budgetItemId;
	private final String name;

	public ActiveBudgetItemResource(String budgetItemId, String name) {
		this.budgetItemId = budgetItemId;
		this.name = name;
	}
	
	public ActiveBudgetItemResource(BudgetItem item) {
		this(item.getId(), item.getName());
	}

	@JsonProperty("id")
	public String getBudgetItemId() {
		return budgetItemId;
	}

	public String getName() {
		return name;
	}
}
