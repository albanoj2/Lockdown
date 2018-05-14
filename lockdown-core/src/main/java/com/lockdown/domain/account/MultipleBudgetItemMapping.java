package com.lockdown.domain.account;

import java.util.HashMap;
import java.util.Map;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;

public class MultipleBudgetItemMapping implements BudgetItemMapping {
	
	private final Map<BudgetItem, Money> mappings = new HashMap<>();
	
	public void addMapping(BudgetItem item, Money amount) {
		mappings.put(item, amount);
	}
	
	public void removeMapping(BudgetItem item) {
		mappings.remove(item);
	}

	@Override
	public Money amountFor(Transaction transaction, BudgetItem entry) {
		// TODO Auto-generated method stub
		return null;
	}
}
