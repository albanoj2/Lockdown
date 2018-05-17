package com.lockdown.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Account extends DomainObject {

	private final String key;
	private final String name;
	private final String type;
	private final String subtype;
	private final List<Transaction> transactions;
	
	public Account(String id, String key, String name, String type, String subtype, List<Transaction> transactions) {
		super(id);
		this.key = key;
		this.name = name;
		this.type = type;
		this.subtype = subtype;
		this.transactions = transactions;
	}
	
	public Account() {
		this(null, "0", "Unnamed", "Unknown", "Unknown", new ArrayList<>());
	}
	
	public static Account blank() {
		return new Account();
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getSubtype() {
		return subtype;
	}

	@JsonIgnore
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	public Account addTransaction(Transaction transaction) {
		transactions.add(transaction);
		return this;
	}
	
	public Account removeTransaction(Transaction transaction) {
		transactions.remove(transaction);
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

	@Override
	public String toString() {
		return "Account [key=" + key + ", name=" + name + ", type=" + type + ", subtype=" + subtype + ", transactions="
				+ transactions + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(key);
	}

	@Override
	public boolean equals(Object object) {
		
		if (this == object) {
			return true;
		}
		else if (!(object instanceof Account)) {
			return false;
		}
		else {
			Transaction other = (Transaction) object;
			return Objects.equals(getKey(), other.getKey());
		}
	}
}

