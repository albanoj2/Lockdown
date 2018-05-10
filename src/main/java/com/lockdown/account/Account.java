package com.lockdown.account;

import java.util.ArrayList;
import java.util.List;

import com.lockdown.money.Money;

public class Account {

	private final List<Transaction> transactions;
	
	public Account(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public static Account blank() {
		return new Account(new ArrayList<>());
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	public Account addTransaction(Transaction transaction) {
		transactions.add(transaction);
		return this;
	}
	
	public Money getBalance() {
		return transactions.stream()
			.map(t -> t.getAmount())
			.reduce((t1, t2) -> t1.sum(t2))
			.orElse(Money.zero());
	}
}
