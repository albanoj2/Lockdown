package com.lockdown.domain;

import static com.lockdown.domain.Money.dollars;
import static org.junit.Assert.assertEquals;

import java.time.Period;

import org.junit.Test;

import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.Frequency;
import com.lockdown.domain.Money;

public class BudgetEntryTest {

	@Test
	public void neverEntryEnsureCorrectAccumulatedTotal() {
		BudgetItem entry = BudgetItem.builder().with(dollars(5), Frequency.NEVER).build();
		assertEquals(Money.zero(), entry.getTotalAccumulatedAmount());
	}
	
	@Test
	public void weeklyEntryWith1OccurrenceEnsureCorrectAccumulatedTotal() {
		BudgetItem entry = BudgetItem.builder()
			.amount(dollars(5))
			.weekly()
			.life(Period.ofWeeks(1))
			.build();
		assertEquals(dollars(5), entry.getTotalAccumulatedAmount());
	}
	
	@Test
	public void monthlyEntryWith1OccurrenceEnsureCorrectAccumulatedTotal() {
		BudgetItem entry = BudgetItem.builder()
			.amount(dollars(5))
			.monthly()
			.life(Period.ofMonths(1))
			.build();
		assertEquals(dollars(5), entry.getTotalAccumulatedAmount());
	}
}
