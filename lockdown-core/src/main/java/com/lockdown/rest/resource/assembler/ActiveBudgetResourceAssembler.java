package com.lockdown.rest.resource.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.lockdown.domain.Budget;
import com.lockdown.rest.resource.ActiveBudgetResource;

@Component
public class ActiveBudgetResourceAssembler {

	public List<ActiveBudgetResource> toResource(List<Budget> budgets) {
		return budgets.stream()
			.map(budget -> new ActiveBudgetResource(budget))
			.collect(Collectors.toList());
	}
}
