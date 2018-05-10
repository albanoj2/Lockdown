package com.lockdown.budget;

import java.time.Period;

@FunctionalInterface
public interface Frequency {
	public int occurrencesIn(Period period);
}