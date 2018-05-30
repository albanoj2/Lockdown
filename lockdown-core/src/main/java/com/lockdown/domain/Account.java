package com.lockdown.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class Account extends Identifiable {
	
	public static enum Type {
		BROKERAGE, CREDIT, DEPOSITORY, LOAN, MORTGAGE, OTHER, UNKNOWN;
	}
	
	public static enum Subtype {
		_401K, _403B, BROKERAGE, IRA, RETIREMENT, ROTH, UGMA, CREDIT_CARD, PAYPAL, LINE_OF_CREDIT, REWARDS, CD, CHECKING, SAVINGS, 
		MONEY_MARKET, PREPAID, AUTO, COMMERCIAL, CONSTRUCTION, CONSUMER, HOME, HOME_EQUITY, OVERDRAFT, STUDENT, 
		CASH_MANAGEMENT, HSA, KOEGH, MUTUAL_FUND, RECURRING, SAFE_DEPOSIT, SARSEP, OTHER, UNKNOWN;
	}

	private final String key;
	private final String name;
	private final Institution institution;
	private final Type type;
	private final Subtype subtype;
	private final List<Transaction> transactions;
	
	public Account(String id, String key, String name, Institution institution, Type type, Subtype subtype, List<Transaction> transactions) {
		super(id);
		this.key = key;
		this.name = name;
		this.institution = institution;
		this.type = type;
		this.subtype = subtype;
		this.transactions = transactions;
	}
	
	public Account() {
		this(null, "0", "Unnamed", Institution.UNKNOWN, Type.UNKNOWN, Subtype.UNKNOWN, new ArrayList<>());
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

	public Institution getInstitution() {
		return institution;
	}

	public Type getType() {
		return type;
	}

	public Subtype getSubtype() {
		return subtype;
	}

	@JsonIgnore
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	public int getTransactionCount() {
		return transactions.size();
	}
	
	public Account addTransaction(Transaction transaction) {
		transactions.add(transaction);
		return this;
	}
	
	public Account removeTransaction(Transaction transaction) {
		transactions.remove(transaction);
		return this;
	}
	
	public Optional<Transaction> getTransactionById(String id) {
		return transactions.stream()
			.filter(transaction -> transaction.getId().equals(id))
			.findFirst();
	}
	
	public synchronized Delta addTransactionOrUpdateIfExists(String key, TransactionBody body) {
		
		Delta delta = Delta.UNCHANGED;
		
		Optional<Transaction> existingTransaction = transactions.stream()
			.filter(t -> t.getKey().equals(key))
			.findFirst();
		
		if (existingTransaction.isPresent()) {
			existingTransaction.get().updateBody(body);
			
			if (!existingTransaction.get().getBody().equals(body)) {
				delta = Delta.UPDATED;
			}
		}
		else {
			addTransaction(new Transaction(null, key, body, Optional.empty(), Transaction.noMapping()));
			delta = Delta.ADDED;
		}

		return delta;
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

