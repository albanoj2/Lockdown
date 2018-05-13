package com.lockdown.domain.account;

import java.util.List;

import com.lockdown.domain.money.Money;

public final class TransactionList {

	private TransactionList() {}
	
	public static <T extends Transaction> Money getBalance(List<T> transactions) {
		return transactions.stream()
			.map(t -> t.getAmount())
			.reduce((t1, t2) -> t1.sum(t2))
			.orElse(Money.zero());
	}
}
