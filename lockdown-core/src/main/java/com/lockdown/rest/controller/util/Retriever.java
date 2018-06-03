package com.lockdown.rest.controller.util;

import com.lockdown.domain.Budget;
import com.lockdown.persist.store.BudgetDataStore;
import com.lockdown.rest.error.ResourceNotFoundException;

public final class Retriever {

	public static Budget getBudgetIfExists(String id, BudgetDataStore budgetDataStore) {
		return budgetDataStore.findById(id)
			.orElseThrow(ResourceNotFoundException.supplierForResource("budget", id));
	}
}
