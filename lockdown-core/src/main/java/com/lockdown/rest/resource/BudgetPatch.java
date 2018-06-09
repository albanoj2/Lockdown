package com.lockdown.rest.resource;

import com.google.common.base.Strings;
import com.lockdown.domain.Budget;

public class BudgetPatch {

	private String name;
	private String description;
	
	public BudgetPatch(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public BudgetPatch() {
		this(null, null);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean hasName() {
		return !Strings.isNullOrEmpty(name);
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean hasDescription() {
		return !Strings.isNullOrEmpty(description);
	}
	
	public void patch(Budget budget) {
		
		if (hasName()) {
			budget.setName(name);
		}
		
		if (hasDescription()) {
			budget.setDescription(description);
		}
	}
}
