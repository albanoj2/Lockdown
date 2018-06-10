package com.lockdown.rest.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.BudgetItemMapping;
import com.lockdown.domain.Money;

public class BudgetItemMappingResource {

	private final Map<String, Long> budgetItemIdMappings;
	
	public BudgetItemMappingResource(BudgetItemMapping mapping) {
		this.budgetItemIdMappings = mapToBudgetItemIds(mapping);
	}
	
	private static Map<String, Long> mapToBudgetItemIds(BudgetItemMapping mapping) {
		
		Map<String, Long> idMappings = new HashMap<>();
		
		for (Entry<BudgetItem, Money> entry: mapping.getMappings().entrySet()) {
			idMappings.put(entry.getKey().getId(), entry.getValue().asCents());
		}
		
		return idMappings;
	}

	public Map<String, Long> getBudgetItemIdMappings() {
		return budgetItemIdMappings;
	}
}
