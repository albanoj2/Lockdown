package com.lockdown.service.sync.provider;

import java.time.LocalDate;

import com.lockdown.domain.Money;
import com.lockdown.domain.Transaction;

public class RawTransactions {

	public Transaction toTransaction(LocalDate date, Money amount, String key, String name, String description, boolean isPending) {
		return Transaction.unbudgeted(null, date, amount, key, name, description, isPending);
	}
}
