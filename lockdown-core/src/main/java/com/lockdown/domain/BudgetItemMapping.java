package com.lockdown.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BudgetItemMapping {
	
	private final Map<BudgetItem, Money> mappings = new HashMap<>();
	
	public static BudgetItemMapping blank() {
		return new BudgetItemMapping();
	}
	
	public static BudgetItemMapping withMapping(BudgetItem item, Money amount) {
		BudgetItemMapping mapping = new BudgetItemMapping();
		mapping.addMapping(item, amount);
		return mapping;
	}

	public void addMapping(BudgetItem item, Money amount) {
		Objects.requireNonNull(item);
		Objects.requireNonNull(amount);
		mappings.put(item, amount);
	}

	public void removeMapping(BudgetItem item) {
		mappings.remove(item);
	}

	public Money amountFor(Transaction transaction, BudgetItem item) {
		return mappings.getOrDefault(item, Money.zero());
	}

	public Money mappedAmount(Transaction transaction) {
		return mappings.values().stream()
			.reduce((amount1, amount2) -> amount1.sum(amount2))
			.orElseGet(() -> Money.zero());
	}
	
	public boolean isValidFor(Transaction transaction) {
		return mappedAmount(transaction).equals(transaction.getAmount());
	}

	public Map<BudgetItem, Money> getMappings() {
		return new HashMap<>(mappings);
	}
}
