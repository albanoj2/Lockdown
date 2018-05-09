package com.lockdown.money;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
	
	@Test
	public void zeroAmountsSubtractedEnsureZeroAmount() {
		DollarAmount zero1 = DollarAmount.zero();
		DollarAmount zero2 = DollarAmount.zero();
		assertCorrectSubtraction(zero1, zero2);
	}
	
	private static void assertCorrectSubtraction(DollarAmount amount1, DollarAmount amount2) {
		assertEquals(amount1.asCents() - amount2.asCents(), amount1.subtract(amount2).asCents());
	}
	
	@Test
	public void twoPositiveAmountsSubtractedEnsureCorrectAmount() {
		DollarAmount amount1 = DollarAmount.cents(10);
		DollarAmount amount2 = DollarAmount.cents(120);
		assertCorrectSubtraction(amount1, amount2);
	}
	
	@Test
	public void twoNegativeAmountsSubtractedEnsureCorrectAmount() {
		DollarAmount amount1 = DollarAmount.cents(-10);
		DollarAmount amount2 = DollarAmount.cents(-120);
		assertCorrectSubtraction(amount1, amount2);
	}
	
	@Test
	public void oneNegativeOnePositiveAmountSubtractedEnsureCorrectAmount() {
		DollarAmount amount1 = DollarAmount.cents(-10);
		DollarAmount amount2 = DollarAmount.cents(120);
		assertCorrectSubtraction(amount1, amount2);
	}
	
	@Test
	public void multiplyPositiveValueWithZeroFactorEnsureCorrectAmount() {
		DollarAmount amount = DollarAmount.cents(10);
		assertEquals(DollarAmount.zero(), amount.multiply(0));
	}
	
	@Test
	public void multiplyNegativeValueWithZeroFactorEnsureCorrectAmount() {
		DollarAmount amount = DollarAmount.cents(-10);
		assertEquals(DollarAmount.zero(), amount.multiply(0));
	}
	
	@Test
	public void multiplyZeroValueWithZeroFactorEnsureCorrectAmount() {
		DollarAmount amount = DollarAmount.cents(0);
		assertEquals(DollarAmount.zero(), amount.multiply(0));
	}
	
	@Test
	public void multiplyPositiveValueWithPositiveFactorEnsureCorrectAmount() {
		DollarAmount amount = DollarAmount.cents(10);
		assertEquals(DollarAmount.cents(20), amount.multiply(2));
	}
	
	@Test
	public void multiplyPositiveValueWithNegativeFactorEnsureCorrectAmount() {
		DollarAmount amount = DollarAmount.cents(10);
		assertEquals(DollarAmount.cents(-20), amount.multiply(-2));
	}
	
	@Test
	public void multiplyNegativeValueWithPositiveFactorEnsureCorrectAmount() {
		DollarAmount amount = DollarAmount.cents(-10);
		assertEquals(DollarAmount.cents(-20), amount.multiply(2));
	}
	
	@Test
	public void multiplyNegativeValueWithNegativeFactorEnsureCorrectAmount() {
		DollarAmount amount = DollarAmount.cents(-10);
		assertEquals(DollarAmount.cents(20), amount.multiply(-2));
	}
	
	@Test
	public void negativeDollarAmountEnsureIsNegative() {
		assertTrue(negativeAmount().isNegative());
	}
	
	private static DollarAmount negativeAmount() {
		return DollarAmount.cents(-1);
	}
	
	@Test
	public void negativeDollarAmountEnsureIsNotDeposit() {
		assertFalse(negativeAmount().isPositive());
	}
	
	@Test
	public void negativeDollarAmountEnsureIsNotZero() {
		assertFalse(negativeAmount().isZero());
	}
	
	@Test
	public void positiveDollarAmountEnsureIsNotExpense() {
		assertFalse(positiveAmount().isNegative());
	}
	
	private static DollarAmount positiveAmount() {
		return DollarAmount.cents(1);
	}
	
	@Test
	public void positiveDollarAmountEnsureisPositive() {
		assertTrue(positiveAmount().isPositive());
	}
	
	@Test
	public void positiveDollarAmountEnsureisNotZero() {
		assertFalse(positiveAmount().isZero());
	}
	
	@Test
	public void zeroDollarAmountEnsureIsNotNegative() {
		assertFalse(DollarAmount.zero().isNegative());
	}
	
	@Test
	public void zeroDollarAmountEnsureIsNotPositive() {
		assertFalse(DollarAmount.zero().isPositive());
	}
	
	@Test
	public void zeroDollarAmountEnsureIsZero() {
		assertTrue(DollarAmount.zero().isZero());
	}
}
