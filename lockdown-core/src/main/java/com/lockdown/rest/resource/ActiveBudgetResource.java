package com.lockdown.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lockdown.domain.Budget;

public class ActiveBudgetResource extends ResourceSupport {

	private final String budgetId;
	private final String name;
	private final List<ActiveBudgetItemResource> items = new ArrayList<>();
	
	public ActiveBudgetResource(String budgetId, String name) {
		this.budgetId = budgetId;
		this.name = name;
	}
	
	public ActiveBudgetResource(Budget budget) {
		this(budget.getId(), budget.getName());
		
		budget.getItems()
			.stream()
			.filter(item -> item.isActive())
			.map(item -> new ActiveBudgetItemResource(item))
			.forEach(this::addItem);
	}

	@JsonProperty("id")
	public String getBudgetId() {
		return budgetId;
	}
	
	public String getName() {
		return name;
	}
	
	public List<ActiveBudgetItemResource> getItems() {
		return items;
	}
	
	public void addItem(ActiveBudgetItemResource item) {
		items.add(item);
	}
	
	public void removeItem(ActiveBudgetItemResource item) {
		items.remove(item);
	}
}
