package com.lockdown.rest.resource;

public class SingleBudgetItemMappingResource {

	private final String budgetItemId;

	public SingleBudgetItemMappingResource(String budgetItemId) {
		this.budgetItemId = budgetItemId;
	}
	
	public SingleBudgetItemMappingResource() {
		this(null);
	}

	public String getBudgetItemId() {
		return budgetItemId;
	}
}
