package com.lockdown.account;

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
		return "$" + getDollars() + "." + getCents();
	}
}
