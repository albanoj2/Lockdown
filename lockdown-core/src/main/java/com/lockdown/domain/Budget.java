package com.lockdown.domain;

import java.util.ArrayList;
import java.util.List;

public class Budget extends Identifiable {

	private String name;
	private String description;
	private final List<BudgetItem> items;
	
	public Budget(String id, String name, String description, List<BudgetItem> items) {
		super(id);
		this.name = name;
		this.description = description;
		this.items = items;
	}
	
	public Budget() {
		this(null, "Unnamed", null, new ArrayList<>());
	}

	public static Budget empty() {
		return new Budget();
	}
	
	public void addBudgetItem(BudgetItem item) {
		this.items.add(item);
	}
	
	public boolean removeItemById(String id) {
		return items.removeIf(item -> item.getId().equals(id));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BudgetItem> getItems() {
		return items;
	}
}
