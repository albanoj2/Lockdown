package com.lockdown.domain;

import static org.junit.Assert.*;

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
	
	@Test
	public void fromZeroFractionalDollarsEnsureCorrectValue() {
		assertEquals(0, Money.fractionalDollars(0.00).asCents());
	}
	
	@Test
	public void fromPositiveCentsFractionalDollarsEnsureCorrectValue() {
		assertEquals(57, Money.fractionalDollars(0.57).asCents());
	}
	
	@Test
	public void fromPositiveCentsShouldBeRoundedFractionalDollarsEnsureCorrectValue() {
		assertEquals(58, Money.fractionalDollars(0.576).asCents());
	}
	
	@Test
	public void fromNegativeCentsFractionalDollarsEnsureCorrectValue() {
		assertEquals(-57, Money.fractionalDollars(-0.57).asCents());
	}
	
	@Test
	public void fromNegativeCentsShouldBeRoundedFractionalDollarsEnsureCorrectValue() {
		assertEquals(-58, Money.fractionalDollars(-0.576).asCents());
	}
	
	@Test
	public void fromPositiveDollarsAndCentsFractionalDollarsEnsureCorrectValue() {
		assertEquals(757, Money.fractionalDollars(7.57).asCents());
	}
	
	@Test
	public void fromNegativeDollarsAndCentsFractionalDollarsEnsureCorrectValue() {
		assertEquals(-757, Money.fractionalDollars(-7.57).asCents());
	}
	
	@Test
	public void givenNegativeAmountWhenCopyMoneyEnsureCopyMatchesOriginal() {
		Money original = Money.cents(-1);
		Money copy = original.copy();
		assertFalse(original == copy);
		assertEquals(original, copy);
	}

	@Test
	public void givenZeroAmountWhenCopyMoneyEnsureCopyMatchesOriginal() {
		Money original = Money.cents(0);
		Money copy = original.copy();
		assertFalse(original == copy);
		assertEquals(original, copy);
	}
	
	@Test
	public void givenPositiveAmountWhenCopyMoneyEnsureCopyMatchesOriginal() {
		Money original = Money.cents(1);
		Money copy = original.copy();
		assertFalse(original == copy);
		assertEquals(original, copy);
	}
	
	@Test
	public void givenValidAmountWhenComparedToNonMoneyObjectEnsureNotEquals() {
		Money money = Money.dollars(1);
		assertNotEquals(money, new Object());
	}
	
	@Test
	public void givenNegativeCentsAmountEnsureCorrectStringRepresentation() {
		Money money = Money.cents(-1);
		assertEquals("-$0.01", money.toString());
	}
	
	@Test
	public void givenZeroCentsAmountEnsureCorrectStringRepresentation() {
		Money money = Money.cents(0);
		assertEquals("$0.00", money.toString());
	}
	
	@Test
	public void givenPositiveCentsAmountEnsureCorrectStringRepresentation() {
		Money money = Money.cents(1);
		assertEquals("$0.01", money.toString());
	}
	
	@Test
	public void givenNegative99CentsAmountEnsureCorrectStringRepresentation() {
		Money money = Money.cents(-99);
		assertEquals("-$0.99", money.toString());
	}
	
	@Test
	public void givenPositive99CentsAmountEnsureCorrectStringRepresentation() {
		Money money = Money.cents(99);
		assertEquals("$0.99", money.toString());
	}
	
	@Test
	public void givenNegativeOneDollarAmountEnsureCorrectStringRepresentation() {
		Money money = Money.dollars(-1);
		assertEquals("-$1.00", money.toString());
	}
	
	@Test
	public void givenPositiveOneDollarAmountEnsureCorrectStringRepresentation() {
		Money money = Money.dollars(1);
		assertEquals("$1.00", money.toString());
	}
	
	@Test
	public void givenNegativeOneDollarAndOneCentAmountEnsureCorrectStringRepresentation() {
		Money money = Money.cents(-101);
		assertEquals("-$1.01", money.toString());
	}
	
	@Test
	public void givenPositiveOneDollarAndOneCentAmountEnsureCorrectStringRepresentation() {
		Money money = Money.cents(101);
		assertEquals("$1.01", money.toString());
	}
}
