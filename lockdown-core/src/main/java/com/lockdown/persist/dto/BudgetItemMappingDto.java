package com.lockdown.persist.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.BudgetItemMapping;
import com.lockdown.domain.Money;

public class BudgetItemMappingDto {
	
	
	private final Map<String, Long> mappings;
	
	public BudgetItemMappingDto(BudgetItemMapping mapping) {
		this.mappings = generateMappings(mapping.getMappings());
	}
	
	public BudgetItemMappingDto() {
		this.mappings = null;
	}
	
	private static Map<String, Long> generateMappings(Map<BudgetItem, Money> originalMappings) {
		
		Map<String, Long> mappings = new HashMap<>();
		
		for (Entry<BudgetItem, Money> entry: originalMappings.entrySet()) {
			mappings.put(entry.getKey().getId(), entry.getValue().asCents());
		}
		
		return mappings;
	}

	public Map<String, Long> getMappings() {
		return mappings;
	}
}
