package com.lockdown.domain.account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lockdown.domain.DomainObject;
import com.lockdown.domain.money.Money;

public class Account extends DomainObject {

	private final String name;
	private final String itemId;
	private final String accessToken;
	private final List<Transaction> transactions;
	
	public Account(String id, String name, String itemId, String accessToken, List<Transaction> transactions) {
		super(id);
		this.name = name;
		this.itemId = itemId;
		this.accessToken = accessToken;
		this.transactions = transactions;
	}
	
	public Account() {
		this(null, "Unnamed", null, null, new ArrayList<>());
	}
	
	public static Account blank() {
		return new Account();
	}

	public String getName() {
		return name;
	}

	public String getItemId() {
		return itemId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	public Account addTransaction(Transaction transaction) {
		transactions.add(transaction);
		return this;
	}
	
	public List<Transaction> getBudgetedTransactions() {
		return transactions.stream().filter(t -> t.isBudgeted()).collect(Collectors.toList());
	}
	
	public List<Transaction> getUnbudgetedTransactions() {
		return transactions.stream().filter(t -> t.isUnbudgeted()).collect(Collectors.toList());
	}
	
	public Money getBudgetedBalance() {
		return transactions.stream()
			.filter(transaction -> transaction.isBudgeted())
			.map(t -> t.getAmount())
			.reduce((t1, t2) -> t1.sum(t2))
			.orElse(Money.zero());
	}
	
	public Money getUnbudgetedBalance() {
		return transactions.stream()
			.filter(transaction -> transaction.isUnbudgeted())
			.map(t -> t.getAmount())
			.reduce((t1, t2) -> t1.sum(t2))
			.orElse(Money.zero());
	}
}

