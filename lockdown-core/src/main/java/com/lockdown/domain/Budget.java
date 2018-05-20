package com.lockdown.domain;

import java.util.ArrayList;
import java.util.List;

public class Budget extends Identifable {

	private final List<BudgetItem> entries;
	
	public Budget(String id, List<BudgetItem> entries) {
		super(id);
		this.entries = entries;
	}
	
	public Budget() {
		this(null, new ArrayList<>());
	}

	public static Budget empty() {
		return new Budget();
	}

	public List<BudgetItem> getEntries() {
		return entries;
	}
}
