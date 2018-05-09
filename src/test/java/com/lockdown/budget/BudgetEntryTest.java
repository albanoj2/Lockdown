package com.lockdown.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.lockdown.money.DollarAmount;

public class BudgetEntryTest {

	@Test
	public void neverEntryEnsureCorrectAccumulatedTotal() {
		BudgetEntry entry = BudgetEntry.startingNow(0, DollarAmount.dollars(5), FrequencyUnits.NEVER);
		assertEquals(DollarAmount.zero(), entry.getTotalAccumulatedAmount());
	}
	
	@Test
	public void weeklyEntryWith1OccurrenceEnsureCorrectAccumulatedTotal() {
		LocalDate start = LocalDate.now().minusWeeks(1);
		BudgetEntry entry = new BudgetEntry(DollarAmount.dollars(5), start, FrequencyUnits.WEEKLY);
		assertEquals(DollarAmount.dollars(5), entry.getTotalAccumulatedAmount());
	}
	
	@Test
	public void weeklyEntryWithStopDateOneWeekAfterStartEnsureCorrectAccumulatedTotal() {
		LocalDate start = LocalDate.now().minusWeeks(2);
		LocalDate stop = start.plusWeeks(1);
		BudgetEntry entry = new BudgetEntry(DollarAmount.dollars(5), start, stop, FrequencyUnits.WEEKLY);
		assertEquals(DollarAmount.dollars(5), entry.getTotalAccumulatedAmount());
	}
	
	@Test
	public void monthlyEntryWith1OccurrenceEnsureCorrectAccumulatedTotal() {
		LocalDate start = LocalDate.now().minusMonths(1);
		BudgetEntry entry = new BudgetEntry(DollarAmount.dollars(5), start, FrequencyUnits.MONTHLY);
		assertEquals(DollarAmount.dollars(5), entry.getTotalAccumulatedAmount());
	}
	
	@Test
	public void monthlyEntryWithStopDateOneWeekAfterStartEnsureCorrectAccumulatedTotal() {
		LocalDate start = LocalDate.now().minusMonths(2);
		LocalDate stop = start.plusMonths(1);
		BudgetEntry entry = new BudgetEntry(DollarAmount.dollars(5), start, stop, FrequencyUnits.MONTHLY);
		assertEquals(DollarAmount.dollars(5), entry.getTotalAccumulatedAmount());
	}
}
