package com.lockdown.persist.dto;

import java.util.Collections;
import java.util.List;

import com.lockdown.domain.Budget;

public class BudgetDto extends Dto {

	private final String name;
	private final List<String> entryIds;
	
	public BudgetDto(Budget budget) {
		super(budget.getId());
		this.name = budget.getName();
		this.entryIds = toIdList(budget.getEntries());
	}
	
	public BudgetDto() {
		this.name = null;
		this.entryIds = Collections.emptyList();
	}

	public String getName() {
		return name;
	}

	public List<String> getEntryIds() {
		return entryIds;
	}
}
