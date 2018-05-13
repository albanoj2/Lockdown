package com.lockdown.domain.money;

public class Money {

	private long cents;
	
	private Money(long cents) {
		this.cents = cents;
	}
	
	public static Money cents(long cents) {
		return new Money(cents);
	}
	
	public static Money zero() {
		return new Money(0);
	}
	
	public static Money dollars(long dollars) {
		return Money.cents(dollars * 100);
	}
	
	public long getDollars() {
		return cents / 100;
	}
	
	public long getCents() {
		return cents % 100;
	}
	
	public long asCents() {
		return cents;
	}
	
	public Money sum(Money amount) {
		return Money.cents(cents + amount.cents);
	}
	
	public Money subtract(Money amount) {
		return Money.cents(cents - amount.cents);
	}
	
	public Money multiply(long factor) {
		return Money.cents(cents * factor);
	}
	
	public boolean isNegative() {
		return cents < 0;
	}
	
	public boolean isPositive() {
		return cents > 0;
	}
	
	public boolean isZero() {
		return cents == 0;
	}
	
	public Money abs() {
		return new Money(Math.abs(cents));
	}

	@Override
	public int hashCode() {
		return 31 * (int) (cents ^ (cents >>> 32));
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		else if (!(obj instanceof Money)) {
			return false;
		}
		else {
			Money other = (Money) obj;
			return other.cents == cents;
		}
	}

	@Override
	public String toString() {
		String sign = cents < 0 ? "-" : "";
		return sign + String.format("$%d.%d", Math.abs(getDollars()), Math.abs(getCents()));
	}
}
