package com.lockdown.rest.controller.util;

import com.lockdown.domain.Budget;
import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.Transaction;
import com.lockdown.persist.store.BudgetDataStore;
import com.lockdown.persist.store.BudgetItemDataStore;
import com.lockdown.persist.store.TransactionDataStore;
import com.lockdown.rest.error.ResourceNotFoundException;

public final class Retriever {

	public static Budget getBudgetIfExists(String id, BudgetDataStore budgetDataStore) {
		return budgetDataStore.findById(id)
			.orElseThrow(ResourceNotFoundException.supplierForResource("budget", id));
	}
	
	public static Transaction getTransactionIfExists(String id, TransactionDataStore transactionDataStore) {
		return transactionDataStore.findById(id)
			.orElseThrow(ResourceNotFoundException.supplierForResource("transaction", id));
	}
	
	public static BudgetItem getBudgetItemIfExists(String id, BudgetItemDataStore budgetItemDataStore) {
		return budgetItemDataStore.findById(id)
			.orElseThrow(ResourceNotFoundException.supplierForResource("budget item", id));
	}
}
