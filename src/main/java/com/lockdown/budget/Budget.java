package com.lockdown.budget;

import java.util.ArrayList;
import java.util.List;

public class Budget {

	private final List<BudgetEntry> entries;
	
	public Budget(List<BudgetEntry> entries) {
		this.entries = entries;
	}
	
	public Budget() {
		this(new ArrayList<>());
	}

	public static Budget empty() {
		return new Budget();
	}

	public List<BudgetEntry> getEntries() {
		return entries;
	}
}
