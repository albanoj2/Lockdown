package com.lockdown.domain.account;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.budget.FrequencyUnits;
import com.lockdown.domain.money.Money;

public class SingleBudgetItemMappingTest {
	
	@Test(expected = NullPointerException.class)
	public void createWithNullBudgetItemEnsureNullPointerExceptionThrown() {
		new SingleBudgetItemMapping(null);
	}
	
	@Test
	public void matchingBudgetItemEnsureCorrectAmount() {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.dollars(1));
		BudgetItem entry = nonNullBudgetItem();
		SingleBudgetItemMapping mapping = new SingleBudgetItemMapping(entry);
		
		assertEquals(Money.dollars(1), mapping.amountFor(budgetedTransaction, entry));
	}
	
	private static BudgetItem nonNullBudgetItem() {
		return budgetEntryWithId(1);
	}
	
	private static BudgetItem budgetEntryWithId(long id) {
		return BudgetItem.builder()
			.id(String.valueOf(id))
			.startingNow()
			.zeroAmount()
			.frequency(FrequencyUnits.WEEKLY)
			.build();
	}

	@Test
	public void nonMatchingBudgetItemEnsureZeroAmount() {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.dollars(1));
		BudgetItem matchingEntry = budgetEntryWithId(1);
		BudgetItem someOtherEntry = budgetEntryWithId(2);
		SingleBudgetItemMapping mapping = new SingleBudgetItemMapping(matchingEntry);
		
		assertEquals(Money.zero(), mapping.amountFor(budgetedTransaction, someOtherEntry));
	}
	
	@Test(expected = NullPointerException.class)
	public void amountForWithNullBudgetItemEnsureNullPointerExceptionThrown() {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.dollars(1));
		BudgetItem entry = nonNullBudgetItem();
		SingleBudgetItemMapping mapping = new SingleBudgetItemMapping(entry);
		
		mapping.amountFor(budgetedTransaction, null);
	}
}
