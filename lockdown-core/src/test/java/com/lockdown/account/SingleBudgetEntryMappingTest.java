package com.lockdown.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.lockdown.budget.BudgetEntry;
import com.lockdown.budget.FrequencyUnits;
import com.lockdown.money.Money;

public class SingleBudgetEntryMappingTest {
	
	@Test
	public void createWithNullBudgetEntryEnsureNullPointerExceptionThrown() {
		assertThrows(NullPointerException.class, () -> new SingleBudgetEntryMapping(null));
	}
	
	@Test
	public void matchingBudgetEntryEnsureCorrectAmount() {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.dollars(1));
		BudgetEntry entry = nonNullBudgetEntry();
		SingleBudgetEntryMapping mapping = new SingleBudgetEntryMapping(entry);
		
		assertEquals(Money.dollars(1), mapping.amountFor(budgetedTransaction, entry));
	}
	
	private static BudgetEntry nonNullBudgetEntry() {
		return budgetEntryWithId(1);
	}
	
	private static BudgetEntry budgetEntryWithId(long id) {
		return BudgetEntry.builder()
			.id(id)
			.startingNow()
			.zeroAmount()
			.frequency(FrequencyUnits.WEEKLY)
			.build();
	}

	@Test
	public void nonMatchingBudgetEntryEnsureZeroAmount() {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.dollars(1));
		BudgetEntry matchingEntry = budgetEntryWithId(1);
		BudgetEntry someOtherEntry = budgetEntryWithId(2);
		SingleBudgetEntryMapping mapping = new SingleBudgetEntryMapping( matchingEntry);
		
		assertEquals(Money.zero(), mapping.amountFor(budgetedTransaction, someOtherEntry));
	}
	
	@Test
	public void amountForWithNullBudgetEntryEnsureNullPointerExceptionThrown() {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.dollars(1));
		BudgetEntry entry = nonNullBudgetEntry();
		SingleBudgetEntryMapping mapping = new SingleBudgetEntryMapping(entry);
		
		assertThrows(NullPointerException.class, () -> mapping.amountFor(budgetedTransaction, null));
	}
}