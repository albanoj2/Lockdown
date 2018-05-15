package com.lockdown.domain.budget;

import java.time.Period;

public interface Recurrence {
	public int occurrencesIn(Period period);
}
