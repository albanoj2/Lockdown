package com.lockdown.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lockdown.domain.Budget;

@JsonInclude(Include.NON_NULL)
public class BudgetResource extends ResourceSupport {
	
	private final String budgetId;
	private final String description;
	private final String name;
	
	public BudgetResource(Budget budget) {
		this.budgetId = budget.getId();
		this.name = budget.getName();
		this.description = budget.getDescription();
	}
	
	@JsonProperty("id")
	public String getBudgetId() {
		return budgetId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
