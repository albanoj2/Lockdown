package com.lockdown.budget;

import java.util.ArrayList;
import java.util.List;

import com.lockdown.DomainObject;

public class Budget extends DomainObject {

	private final List<BudgetEntry> entries;
	
	public Budget(long id, List<BudgetEntry> entries) {
		super(id);
		this.entries = entries;
	}
	
	public Budget() {
		this(0, new ArrayList<>());
	}

	public static Budget empty() {
		return new Budget();
	}

	public List<BudgetEntry> getEntries() {
		return entries;
	}
}
