package com.lockdown.rest.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.BudgetItemMapping;
import com.lockdown.domain.Money;

public class BudgetItemMappingResource {

	private final Map<String, Money> budgetItemIdMappings;
	
	public BudgetItemMappingResource(BudgetItemMapping mapping) {
		this.budgetItemIdMappings = mapToBudgetItemIds(mapping);
	}
	
	private static Map<String, Money> mapToBudgetItemIds(BudgetItemMapping mapping) {
		
		Map<String, Money> idMappings = new HashMap<>();
		
		for (Entry<BudgetItem, Money> entry: mapping.getMappings().entrySet()) {
			idMappings.put(entry.getKey().getId(), entry.getValue());
		}
		
		return idMappings;
	}

	public Map<String, Money> getBudgetItemIdMappings() {
		return budgetItemIdMappings;
	}
}
