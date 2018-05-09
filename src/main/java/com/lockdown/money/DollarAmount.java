package com.lockdown.money;

public class DollarAmount {

	private long cents;
	
	private DollarAmount(long cents) {
		this.cents = cents;
	}
	
	public static DollarAmount cents(long cents) {
		return new DollarAmount(cents);
	}
	
	public static DollarAmount zero() {
		return new DollarAmount(0);
	}
	
	public static DollarAmount dollars(long dollars) {
		return DollarAmount.cents(dollars * 100);
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
	
	public DollarAmount sum(DollarAmount amount) {
		return DollarAmount.cents(cents + amount.cents);
	}
	
	public DollarAmount subtract(DollarAmount amount) {
		return DollarAmount.cents(cents - amount.cents);
	}
	
	public DollarAmount multiply(long factor) {
		return DollarAmount.cents(cents * factor);
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
	
	public DollarAmount abs() {
		return new DollarAmount(Math.abs(cents));
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
		else if (!(obj instanceof DollarAmount)) {
			return false;
		}
		else {
			DollarAmount other = (DollarAmount) obj;
			return other.cents == cents;
		}
	}

	@Override
	public String toString() {
		String sign = cents < 0 ? "-" : "";
		return sign + String.format("$%d.%d", Math.abs(getDollars()), Math.abs(getCents()));
	}
}
