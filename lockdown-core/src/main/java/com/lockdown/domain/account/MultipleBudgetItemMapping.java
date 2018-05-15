package com.lockdown.domain.account;

import java.util.Map;
import java.util.Objects;

import javax.persistence.Convert;
import javax.persistence.Entity;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;
import com.lockdown.domain.money.MoneyAttributeConverter;

@Entity
public class MultipleBudgetItemMapping extends BudgetItemMapping {

	@Convert(converter = MoneyAttributeConverter.class)
	private final Map<BudgetItem, Money> mappings;

	protected MultipleBudgetItemMapping(Long id, Map<BudgetItem, Money> mappings) {
		super(id);
		this.mappings = mappings;
	}

	public void addMapping(BudgetItem item, Money amount) {
		Objects.requireNonNull(item);
		Objects.requireNonNull(amount);
		mappings.put(item, amount);
	}

	public void removeMapping(BudgetItem item) {
		mappings.remove(item);
	}

	@Override
	public Money amountFor(Transaction transaction, BudgetItem item) {
		return mappings.getOrDefault(item, Money.zero());
	}

	@Override
	public Money mappedAmount(Transaction transaction) {
		return mappings.values().stream()
			.reduce((amount1, amount2) -> amount1.sum(amount2))
			.orElseGet(() -> Money.zero());
	}
}
