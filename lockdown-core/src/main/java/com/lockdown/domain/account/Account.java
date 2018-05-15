package com.lockdown.domain.account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lockdown.domain.money.Money;

@Entity
public class Account {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private final Long id;
	
	private final String key;
	private final String name;
	private final String type;
	
	@OneToMany(cascade = CascadeType.ALL)
	private final List<Transaction> transactions;
	
	public Account(Long id, String key, String name, String type, List<Transaction> transactions) {
		this.id = id;
		this.key = key;
		this.name = name;
		this.type = type;
		this.transactions = transactions;
	}
	
	public Account() {
		this(null, "0", "Unnamed", "Unknown", new ArrayList<>());
	}
	
	public static Account blank() {
		return new Account();
	}

	public Long getId() {
		return id;
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

	@JsonIgnore
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

	@Override
	public String toString() {
		return "Account [name=" + name + ", transactions=" + transactions + "]";
	}
}

