package com.lockdown.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lockdown.account.DollarAmount;

public class DollarAmountTest {

	@Test
	public void twoZeroValuesEnsureBothAreEqual() {
		assertEquals(DollarAmount.cents(0), DollarAmount.cents(0));
	}
	
	@Test
	public void twoPositiveValuesEnsureBothAreEqual() {
		assertEquals(DollarAmount.cents(1), DollarAmount.cents(1));
	}
	
	@Test
	public void twoNegativeValuesEnsureBothAreEqual() {
		assertEquals(DollarAmount.cents(-1), DollarAmount.cents(-1));
	}
	
	@Test
	public void zeroDollarValueEnsureCorrectDollarAmount() {
		assertCorrectDollarAmount(DollarAmount.zero(), 0);
	}
	
	private static void assertCorrectDollarAmount(DollarAmount value, long dollars) {
		assertEquals(value.getDollars(), dollars);
	}
	
	@Test
	public void zeroDollarValueEnsureCorrectCents() {
		assertCorrectCents(DollarAmount.zero(), 0);
	}
	
	private static void assertCorrectCents(DollarAmount value, long expected) {
		assertEquals(expected, value.getCents());
	}
	
	@Test
	public void zeroDollarWithPositiveCentsValueEnsureCorrectDollarAmount() {
		assertCorrectDollarAmount(DollarAmount.cents(12), 0);
	}

	@Test
	public void positiveDollarValueEnsureCorrectDollarAmount() {
		assertCorrectDollarAmount(DollarAmount.cents(112), 1);
	}

	@Test
	public void negativeDollarValueEnsureCorrectDollarAmount() {
		assertCorrectDollarAmount(DollarAmount.cents(-112), -1);
	}
	
	@Test
	public void positiveCentsValueLessThanADollarEnsureCorrectCents() {
		assertCorrectCents(DollarAmount.cents(99), 99);
	}
	
	@Test
	public void positiveCentsValueExactlyADollarEnsureCorrectCents() {
		assertCorrectCents(DollarAmount.cents(100), 0);
	}
	
	@Test
	public void positiveCentsValueMoreThanADollarEnsureCorrectCents() {
		assertCorrectCents(DollarAmount.cents(101), 1);
	}
	
	@Test
	public void negativeCentsValueLessThanADollarEnsureCorrectCents() {
		assertCorrectCents(DollarAmount.cents(-99), -99);
	}
	
	@Test
	public void negativeCentsValueExactlyADollarEnsureCorrectCents() {
		assertCorrectCents(DollarAmount.cents(-100), 0);
	}
	
	@Test
	public void negativeCentsValueMoreThanADollarEnsureCorrectCents() {
		assertCorrectCents(DollarAmount.cents(-101), -1);
	}
	
	@Test
	public void zeroAmountsSummedEnsureZeroAmount() {
		DollarAmount zero1 = DollarAmount.zero();
		DollarAmount zero2 = DollarAmount.zero();
		assertCorrectSum(zero1, zero2);
	}
	
	private static void assertCorrectSum(DollarAmount amount1, DollarAmount amount2) {
		assertEquals(amount1.asCents() + amount2.asCents(), amount1.sum(amount2).asCents());
	}
	
	@Test
	public void twoPositiveAmountsSummedEnsureCorrectAmount() {
		DollarAmount amount1 = DollarAmount.cents(10);
		DollarAmount amount2 = DollarAmount.cents(120);
		assertCorrectSum(amount1, amount2);
	}
	
	@Test
	public void twoNegativeAmountsSummedEnsureCorrectAmount() {
		DollarAmount amount1 = DollarAmount.cents(-10);
		DollarAmount amount2 = DollarAmount.cents(-120);
		assertCorrectSum(amount1, amount2);
	}
	
	@Test
	public void oneNegativeOnePositiveAmountSummedEnsureCorrectAmount() {
		DollarAmount amount1 = DollarAmount.cents(-10);
		DollarAmount amount2 = DollarAmount.cents(120);
		assertCorrectSum(amount1, amount2);
	}
}
