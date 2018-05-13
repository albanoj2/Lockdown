package com.lockdown.domain.time;

import java.time.LocalDate;
import java.time.Period;

public class Periods {

	public static Period from(LocalDate start) {
		return Period.between(start, LocalDate.now());
	}
	
	public static Period fromNow() {
		return from(LocalDate.now());
	}
}
