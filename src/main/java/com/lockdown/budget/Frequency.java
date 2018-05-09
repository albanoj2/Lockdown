package com.lockdown.budget;

import java.time.LocalDate;

public interface Frequency {
	public long occurrencesBetween(LocalDate start, LocalDate end);
}
