package com.lockdown.domain;

import java.time.Period;

@FunctionalInterface
public interface Recurrence {
	public int occurrencesIn(Period period);
}
