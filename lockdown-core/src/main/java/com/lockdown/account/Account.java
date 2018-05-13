package com.lockdown.account;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lockdown.DomainObject;
import com.lockdown.money.Money;

public class Account extends DomainObject {

	private final String name;
	private final List<UnbudgetedTransaction> unbudgetedTransactions;
	private final List<BudgetedTransaction> budgetedTransactions;
	
	public Account(String id, String name, List<UnbudgetedTransaction> unbudgetedTransactions, List<BudgetedTransaction> budgetedTransactions) {
		super(id);
		this.name = name;
		this.unbudgetedTransactions = unbudgetedTransactions;
		this.budgetedTransactions = budgetedTransactions;
	}
	
	public Account(String id, String name) {
		this(id, name, new ArrayList<>(), new ArrayList<>());
	}
	
	public Account() {
		this(null, null);
	}
	
	public static Account blank() {
		return new Account();
	}
	
	public static Account withIdAndName(String id, String name) {
		return new Account(id, name);
	}
	
	public static Account withName(String name) {
		return Account.withIdAndName(null, name);
	}
	
	public String getName() {
		return name;
	}
	
	public List<UnbudgetedTransaction> getUnbudgetedTransactions() {
		return unbudgetedTransactions;
	}
	
	public Account addUnbudgetedTransaction(UnbudgetedTransaction transaction) {
		unbudgetedTransactions.add(transaction);
		return this;
	}
	
	public List<BudgetedTransaction> getBudgetedTransactions() {
		return budgetedTransactions;
	}
	
	public Account addBudgetedTransaction(BudgetedTransaction transaction) {
		budgetedTransactions.add(transaction);
		return this;
	}
	
	@JsonIgnore
	public Money getBudgetedBalance() {
		return TransactionList.getBalance(budgetedTransactions);
	}
	
	@JsonIgnore
	public Money getUnbudgetedBalance() {
		return TransactionList.getBalance(unbudgetedTransactions);
	}
}
