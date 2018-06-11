package com.lockdown.rest.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.BudgetItemMapping;
import com.lockdown.domain.Money;

public class BudgetItemMappingsResource {

	private final List<BudgetItemMappingResource> budgetItemIdMappings;
	
	public BudgetItemMappingsResource(BudgetItemMapping mapping) {
		this.budgetItemIdMappings = mapToBudgetItemIds(mapping);
	}
	
	private static List<BudgetItemMappingResource> mapToBudgetItemIds(BudgetItemMapping mapping) {
		
		List<BudgetItemMappingResource> resources = new ArrayList<>();
		
		for (Entry<BudgetItem, Money> entry: mapping.getMappings().entrySet()) {
			resources.add(new BudgetItemMappingResource(entry.getKey().getId(), entry.getValue().asCents()));
		}
		
		return resources;
	}

	public List<BudgetItemMappingResource> getBudgetItemIdMappings() {
		return budgetItemIdMappings;
	}
	
	public static class BudgetItemMappingResource {
		
		private final String budgetItemId;
		private final long amount;
		
		public BudgetItemMappingResource(String budgetItemId, long amount) {
			this.budgetItemId = budgetItemId;
			this.amount = amount;
		}

		public String getBudgetItemId() {
			return budgetItemId;
		}

		public long getAmount() {
			return amount;
		}
	}
}
