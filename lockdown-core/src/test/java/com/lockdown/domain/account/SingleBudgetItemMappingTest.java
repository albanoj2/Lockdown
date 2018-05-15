package com.lockdown.domain.account;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.budget.Frequency;
import com.lockdown.domain.money.Money;

public class SingleBudgetItemMappingTest {
	
	@Test(expected = NullPointerException.class)
	public void createWithNullBudgetItemEnsureNullPointerExceptionThrown() {
		new SingleBudgetItemMapping(null, null);
	}
	
	@Test
	public void matchingBudgetItemEnsureCorrectAmount() {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.dollars(1));
		BudgetItem entry = nonNullBudgetItem();
		SingleBudgetItemMapping mapping = new SingleBudgetItemMapping(null, entry);
		
		assertEquals(Money.dollars(1), mapping.amountFor(budgetedTransaction, entry));
	}
	
	private static BudgetItem nonNullBudgetItem() {
		return budgetEntryWithId(1);
	}
	
	private static BudgetItem budgetEntryWithId(long id) {
		return BudgetItem.builder()
			.id(id)
			.startingNow()
			.zeroAmount()
			.frequency(Frequency.WEEKLY)
			.build();
	}

	@Test
	public void nonMatchingBudgetItemEnsureZeroAmount() {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.dollars(1));
		BudgetItem matchingEntry = budgetEntryWithId(1);
		BudgetItem someOtherEntry = budgetEntryWithId(2);
		SingleBudgetItemMapping mapping = new SingleBudgetItemMapping(null, matchingEntry);
		
		assertEquals(Money.zero(), mapping.amountFor(budgetedTransaction, someOtherEntry));
	}
	
	@Test(expected = NullPointerException.class)
	public void amountForWithNullBudgetItemEnsureNullPointerExceptionThrown() {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.dollars(1));
		BudgetItem entry = nonNullBudgetItem();
		SingleBudgetItemMapping mapping = new SingleBudgetItemMapping(null, entry);
		
		mapping.amountFor(budgetedTransaction, null);
	}
}
