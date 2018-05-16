package com.lockdown.domain.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.lockdown.domain.Money;

public class MoneyTest {

	@Test
	public void twoZeroValuesEnsureBothAreEqual() {
		assertEquals(Money.cents(0), Money.cents(0));
	}
	
	@Test
	public void twoPositiveValuesEnsureBothAreEqual() {
		assertEquals(Money.cents(1), Money.cents(1));
	}
	
	@Test
	public void twoNegativeValuesEnsureBothAreEqual() {
		assertEquals(Money.cents(-1), Money.cents(-1));
	}
	
	@Test
	public void zeroDollarValueEnsureCorrectDollarAmount() {
		assertCorrectDollarAmount(Money.zero(), 0);
	}
	
	private static void assertCorrectDollarAmount(Money value, long dollars) {
		assertEquals(value.getDollars(), dollars);
	}
	
	@Test
	public void zeroDollarValueEnsureCorrectCents() {
		assertCorrectCents(Money.zero(), 0);
	}
	
	private static void assertCorrectCents(Money value, long expected) {
		assertEquals(expected, value.getCents());
	}
	
	@Test
	public void zeroDollarWithPositiveCentsValueEnsureCorrectDollarAmount() {
		assertCorrectDollarAmount(Money.cents(12), 0);
	}

	@Test
	public void positiveDollarValueEnsureCorrectDollarAmount() {
		assertCorrectDollarAmount(Money.cents(112), 1);
	}

	@Test
	public void negativeDollarValueEnsureCorrectDollarAmount() {
		assertCorrectDollarAmount(Money.cents(-112), -1);
	}
	
	@Test
	public void positiveCentsValueLessThanADollarEnsureCorrectCents() {
		assertCorrectCents(Money.cents(99), 99);
	}
	
	@Test
	public void positiveCentsValueExactlyADollarEnsureCorrectCents() {
		assertCorrectCents(Money.cents(100), 0);
	}
	
	@Test
	public void positiveCentsValueMoreThanADollarEnsureCorrectCents() {
		assertCorrectCents(Money.cents(101), 1);
	}
	
	@Test
	public void negativeCentsValueLessThanADollarEnsureCorrectCents() {
		assertCorrectCents(Money.cents(-99), -99);
	}
	
	@Test
	public void negativeCentsValueExactlyADollarEnsureCorrectCents() {
		assertCorrectCents(Money.cents(-100), 0);
	}
	
	@Test
	public void negativeCentsValueMoreThanADollarEnsureCorrectCents() {
		assertCorrectCents(Money.cents(-101), -1);
	}
	
	@Test
	public void zeroAmountsSummedEnsureZeroAmount() {
		Money zero1 = Money.zero();
		Money zero2 = Money.zero();
		assertCorrectSum(zero1, zero2);
	}
	
	private static void assertCorrectSum(Money amount1, Money amount2) {
		assertEquals(amount1.asCents() + amount2.asCents(), amount1.sum(amount2).asCents());
	}
	
	@Test
	public void twoPositiveAmountsSummedEnsureCorrectAmount() {
		Money amount1 = Money.cents(10);
		Money amount2 = Money.cents(120);
		assertCorrectSum(amount1, amount2);
	}
	
	@Test
	public void twoNegativeAmountsSummedEnsureCorrectAmount() {
		Money amount1 = Money.cents(-10);
		Money amount2 = Money.cents(-120);
		assertCorrectSum(amount1, amount2);
	}
	
	@Test
	public void oneNegativeOnePositiveAmountSummedEnsureCorrectAmount() {
		Money amount1 = Money.cents(-10);
		Money amount2 = Money.cents(120);
		assertCorrectSum(amount1, amount2);
	}
	
	@Test
	public void zeroAmountsSubtractedEnsureZeroAmount() {
		Money zero1 = Money.zero();
		Money zero2 = Money.zero();
		assertCorrectSubtraction(zero1, zero2);
	}
	
	private static void assertCorrectSubtraction(Money amount1, Money amount2) {
		assertEquals(amount1.asCents() - amount2.asCents(), amount1.subtract(amount2).asCents());
	}
	
	@Test
	public void twoPositiveAmountsSubtractedEnsureCorrectAmount() {
		Money amount1 = Money.cents(10);
		Money amount2 = Money.cents(120);
		assertCorrectSubtraction(amount1, amount2);
	}
	
	@Test
	public void twoNegativeAmountsSubtractedEnsureCorrectAmount() {
		Money amount1 = Money.cents(-10);
		Money amount2 = Money.cents(-120);
		assertCorrectSubtraction(amount1, amount2);
	}
	
	@Test
	public void oneNegativeOnePositiveAmountSubtractedEnsureCorrectAmount() {
		Money amount1 = Money.cents(-10);
		Money amount2 = Money.cents(120);
		assertCorrectSubtraction(amount1, amount2);
	}
	
	@Test
	public void multiplyPositiveValueWithZeroFactorEnsureCorrectAmount() {
		Money amount = Money.cents(10);
		assertEquals(Money.zero(), amount.multiply(0));
	}
	
	@Test
	public void multiplyNegativeValueWithZeroFactorEnsureCorrectAmount() {
		Money amount = Money.cents(-10);
		assertEquals(Money.zero(), amount.multiply(0));
	}
	
	@Test
	public void multiplyZeroValueWithZeroFactorEnsureCorrectAmount() {
		Money amount = Money.cents(0);
		assertEquals(Money.zero(), amount.multiply(0));
	}
	
	@Test
	public void multiplyPositiveValueWithPositiveFactorEnsureCorrectAmount() {
		Money amount = Money.cents(10);
		assertEquals(Money.cents(20), amount.multiply(2));
	}
	
	@Test
	public void multiplyPositiveValueWithNegativeFactorEnsureCorrectAmount() {
		Money amount = Money.cents(10);
		assertEquals(Money.cents(-20), amount.multiply(-2));
	}
	
	@Test
	public void multiplyNegativeValueWithPositiveFactorEnsureCorrectAmount() {
		Money amount = Money.cents(-10);
		assertEquals(Money.cents(-20), amount.multiply(2));
	}
	
	@Test
	public void multiplyNegativeValueWithNegativeFactorEnsureCorrectAmount() {
		Money amount = Money.cents(-10);
		assertEquals(Money.cents(20), amount.multiply(-2));
	}
	
	@Test
	public void negativeDollarAmountEnsureIsNegative() {
		assertTrue(negativeAmount().isNegative());
	}
	
	private static Money negativeAmount() {
		return Money.cents(-1);
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
	
	private static Money positiveAmount() {
		return Money.cents(1);
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
		assertFalse(Money.zero().isNegative());
	}
	
	@Test
	public void zeroDollarAmountEnsureIsNotPositive() {
		assertFalse(Money.zero().isPositive());
	}
	
	@Test
	public void zeroDollarAmountEnsureIsZero() {
		assertTrue(Money.zero().isZero());
	}
}
