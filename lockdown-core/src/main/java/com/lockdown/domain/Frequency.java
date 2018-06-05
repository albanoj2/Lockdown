package com.lockdown.domain;

import java.time.Period;

public enum Frequency implements Recurrence {
	
	WEEKLY {
		@Override
		public int occurrencesIn(Period period) {
			return period.getDays() / 7 + 1;
		}
	}, 
	MONTHLY {
		@Override
		public int occurrencesIn(Period period) {
			return period.getMonths() + 1;
		}
	}, 
	NEVER {
		@Override
		public int occurrencesIn(Period period) {
			return 0;
		}
	};
}
