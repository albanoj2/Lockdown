package com.lockdown.persist.dto;

import java.util.Collections;
import java.util.List;

import com.lockdown.domain.Budget;

public class BudgetDto extends Dto {

	private final List<String> entryIds;
	
	public BudgetDto(Budget budget) {
		super(budget.getId());
		this.entryIds = toIdList(budget.getEntries());
	}
	
	public BudgetDto() {
		this.entryIds = Collections.emptyList();
	}

	public List<String> getEntryIds() {
		return entryIds;
	}
}
