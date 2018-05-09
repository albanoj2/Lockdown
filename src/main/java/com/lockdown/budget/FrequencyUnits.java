package com.lockdown.budget;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum FrequencyUnits implements Frequency {
	
	WEEKLY {
		@Override
		public long occurrencesBetween(LocalDate start, LocalDate end) {
			return ChronoUnit.WEEKS.between(start, end);
		}
		
	}, 
	MONTHLY {
		@Override
		public long occurrencesBetween(LocalDate start, LocalDate end) {
			return ChronoUnit.MONTHS.between(start, end);
		}
		
	}, 
	NEVER {
		@Override
		public long occurrencesBetween(LocalDate start, LocalDate end) {
			return 0;
		}
		
	};
}
