package com.lockdown.persist.dto;

import java.util.Collections;
import java.util.List;

import com.lockdown.domain.Budget;

public class BudgetDto extends Dto {

	private final String name;
	private final String description;
	private final List<String> entryIds;
	
	public BudgetDto(Budget budget) {
		super(budget.getId());
		this.name = budget.getName();
		this.description = budget.getDescription();
		this.entryIds = toIdList(budget.getItems());
	}
	
	public BudgetDto() {
		this.name = null;
		this.description = null;
		this.entryIds = Collections.emptyList();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getEntryIds() {
		return entryIds;
	}
}
