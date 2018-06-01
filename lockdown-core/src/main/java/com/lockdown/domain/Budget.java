package com.lockdown.domain;

import java.util.ArrayList;
import java.util.List;

public class Budget extends Identifiable {

	private String name;
	private final List<BudgetItem> entries;
	
	public Budget(String id, String name, List<BudgetItem> entries) {
		super(id);
		this.name = name;
		this.entries = entries;
	}
	
	public Budget() {
		this(null, "Unnamed", new ArrayList<>());
	}

	public static Budget empty() {
		return new Budget();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BudgetItem> getEntries() {
		return entries;
	}
}
