package com.lockdown.service.sync.provider.plaid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.lockdown.domain.Money;

public class PlaidConverter {

	public static LocalDate toLocalDate(String date) {
		return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	public static Money toMoney(double amount) {
		return Money.fractionalDollars(amount);
	}
}
