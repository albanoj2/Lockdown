package com.lockdown.budget;

import java.util.ArrayList;
import java.util.List;

import com.lockdown.DomainObject;

public class Budget extends DomainObject {

	private final List<BudgetEntry> entries;
	
	public Budget(String id, List<BudgetEntry> entries) {
		super(id);
		this.entries = entries;
	}
	
	public Budget() {
		this(null, new ArrayList<>());
	}

	public static Budget empty() {
		return new Budget();
	}

	public List<BudgetEntry> getEntries() {
		return entries;
	}
}
