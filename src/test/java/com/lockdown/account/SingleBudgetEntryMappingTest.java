package com.lockdown.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.lockdown.budget.BudgetEntry;
import com.lockdown.budget.FrequencyUnits;
import com.lockdown.money.DollarAmount;

public class SingleBudgetEntryMappingTest {
	
	@Test
	public void createWithNullBudgetEntryEnsureNullPointerExceptionThrown() {
		assertThrows(NullPointerException.class, () -> new SingleBudgetEntryMapping(null));
	}
	
	@Test
	public void matchingBudgetEntryEnsureCorrectAmount() {
		Transaction transaction = Transaction.now(DollarAmount.dollars(1));
		BudgetEntry entry = nonNullBudgetEntry();
		SingleBudgetEntryMapping mapping = new SingleBudgetEntryMapping(entry);
		
		assertEquals(DollarAmount.dollars(1), mapping.amountFor(transaction, entry));
	}
	
	private static BudgetEntry nonNullBudgetEntry() {
		return budgetEntryWithId(1);
	}
	
	private static BudgetEntry budgetEntryWithId(long id) {
		return BudgetEntry.startingNow(id, DollarAmount.zero(), FrequencyUnits.WEEKLY);
	}

	@Test
	public void nonMatchingBudgetEntryEnsureZeroAmount() {
		Transaction transaction = Transaction.now(DollarAmount.dollars(1));
		BudgetEntry matchingEntry = budgetEntryWithId(1);
		BudgetEntry someOtherEntry = budgetEntryWithId(2);
		SingleBudgetEntryMapping mapping = new SingleBudgetEntryMapping( matchingEntry);
		
		assertEquals(DollarAmount.zero(), mapping.amountFor(transaction, someOtherEntry));
	}
	
	@Test
	public void amountForWithNullBudgetEntryEnsureNullPointerExceptionThrown() {
		Transaction transaction = Transaction.now(DollarAmount.dollars(1));
		BudgetEntry entry = nonNullBudgetEntry();
		SingleBudgetEntryMapping mapping = new SingleBudgetEntryMapping(entry);
		
		assertThrows(NullPointerException.class, () -> mapping.amountFor(transaction, null));
	}
}
