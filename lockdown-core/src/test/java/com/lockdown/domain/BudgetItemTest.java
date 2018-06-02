package com.lockdown.domain;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.Test;

public class BudgetItemTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void givenBudgetItemWithEndDateBeforeStartDateEnsureIllegalArgumentExceptionThrown() {
		LocalDate start = LocalDate.now();
		Optional<LocalDate> end = Optional.of(start.minus(1, ChronoUnit.DAYS));
		cononicalBoundedBudgetItem(start, end, false);
	}
	
	private static BudgetItem cononicalBoundedBudgetItem(LocalDate start, Optional<LocalDate> end, boolean isActive) {
		return cononicalBudgetItem(Money.zero(), Frequency.WEEKLY, start, end, isActive);
	}
	
	private static BudgetItem cononicalBudgetItem(Money amount, Frequency frequency, LocalDate start, Optional<LocalDate> end, boolean isActive) {
		return new BudgetItem(null, "foo", "bar", amount, frequency, start, end, isActive);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void givenInactiveBudgetItemWithoutEndDateEnsureIllegalArgumentExceptionThrown() {
		cononicalBoundedBudgetItem(LocalDate.now(), Optional.empty(), false);
	}
	
	@Test
	public void givenActiveBudgetItemAndStartIsNowEnsureCorrectAccumulationPeriod() {
		BudgetItem item = cononicalBoundedBudgetItem(LocalDate.now(), Optional.empty(), true);
		assertEquals(0, item.getAccumulationPeriod().getDays());
	}
	
	@Test
	public void givenActiveBudgetItemAndStartIsOneDayAgoEnsureCorrectAccumulationPeriod() {
		BudgetItem item = cononicalBoundedBudgetItem(LocalDate.now().minus(1, ChronoUnit.DAYS), Optional.empty(), true);
		assertEquals(1, item.getAccumulationPeriod().getDays());
	}
	
	@Test
	public void givenInactiveBudgetItemAndStartIsOneDayAgoEnsureCorrectAccumulationPeriod() {
		LocalDate start = LocalDate.now().minus(1, ChronoUnit.DAYS);
		LocalDate end = LocalDate.now();
		BudgetItem item = cononicalBoundedBudgetItem(start, Optional.of(end), false);
		assertEquals(1, item.getAccumulationPeriod().getDays());
	}
	
	@Test
	public void givenInactiveBudgetItemAndEndIsTwoDaysAgoEnsureCorrectAccumulationPeriod() {
		LocalDate start = LocalDate.now().minus(2, ChronoUnit.DAYS);
		LocalDate end = LocalDate.now();
		BudgetItem item = cononicalBoundedBudgetItem(start, Optional.of(end), false);
		assertEquals(2, item.getAccumulationPeriod().getDays());
	}
	
	@Test
	public void givenActiveWeeklyBudgetItemWhenStartDateIsNowEnsureAccumulatedTotalIsZero() {
		BudgetItem item = cononicalActiveBudgetItem(Money.dollars(5), Frequency.WEEKLY, LocalDate.now());
		assertEquals(Money.zero(), item.getTotalAccumulatedAmount());
	}
	
	private static BudgetItem cononicalActiveBudgetItem(Money amount, Frequency frequency, LocalDate start) {
		return cononicalBudgetItem(amount, frequency, start, Optional.empty(), true);
	}
	
	@Test
	public void givenActiveWeeklyBudgetItemWhenStartDateIsOneWeekAgoEnsureCorrectAccumulatedTotal() {
		LocalDate oneWeekAgo = LocalDate.now().minus(1, ChronoUnit.WEEKS);
		BudgetItem item = cononicalActiveBudgetItem(Money.dollars(5), Frequency.WEEKLY, oneWeekAgo);
		assertEquals(Money.dollars(5), item.getTotalAccumulatedAmount());
	}
	
	@Test
	public void givenActiveMonthlyBudgetItemWhenStartDateIsNowEnsureAccumulatedTotalIsZero() {
		BudgetItem item = cononicalActiveBudgetItem(Money.dollars(5), Frequency.MONTHLY, LocalDate.now());
		assertEquals(Money.zero(), item.getTotalAccumulatedAmount());
	}
	
	@Test
	public void givenActiveMonthlyBudgetItemWhenStartDateIsOneWeekAgoEnsureCorrectAccumulatedTotal() {
		LocalDate oneWeekAgo = LocalDate.now().minus(1, ChronoUnit.MONTHS);
		BudgetItem item = cononicalActiveBudgetItem(Money.dollars(5), Frequency.MONTHLY, oneWeekAgo);
		assertEquals(Money.dollars(5), item.getTotalAccumulatedAmount());
	}
	
	@Test
	public void givenActiveNeverBudgetItemWhenStartDateIsNowEnsureAccumulatedTotalIsZero() {
		BudgetItem item = cononicalActiveBudgetItem(Money.dollars(5), Frequency.NEVER, LocalDate.now());
		assertEquals(Money.zero(), item.getTotalAccumulatedAmount());
	}
	
	@Test
	public void givenActiveNeverBudgetItemWhenStartDateIsOneWeekAgoEnsureAccumulatedTotalIsZero() {
		LocalDate oneWeekAgo = LocalDate.now().minus(1, ChronoUnit.MONTHS);
		BudgetItem item = cononicalActiveBudgetItem(Money.dollars(5), Frequency.NEVER, oneWeekAgo);
		assertEquals(Money.zero(), item.getTotalAccumulatedAmount());
	}
}
