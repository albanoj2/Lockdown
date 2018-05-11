package com.lockdown.account;

import java.util.ArrayList;
import java.util.List;

import com.lockdown.DomainObject;
import com.lockdown.money.Money;

public class Account extends DomainObject {

	private final List<UnbudgetedTransaction> unbudgetedTransactions;
	private final List<BudgetedTransaction> budgetedTransactions;
	
	public Account(long id, List<UnbudgetedTransaction> unbudgetedTransactions, List<BudgetedTransaction> budgetedTransactions) {
		super(id);
		this.unbudgetedTransactions = unbudgetedTransactions;
		this.budgetedTransactions = budgetedTransactions;
	}
	
	public static Account blank() {
		return new Account(0, new ArrayList<>(), new ArrayList<>());
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
	
	public Money getBudgetedBalance() {
		return TransactionList.getBalance(budgetedTransactions);
	}
	
	public Money getUnbudgetedBalance() {
		return TransactionList.getBalance(unbudgetedTransactions);
	}
}
