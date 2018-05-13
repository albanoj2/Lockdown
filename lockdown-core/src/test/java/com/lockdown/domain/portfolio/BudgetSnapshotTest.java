package com.lockdown.domain.portfolio;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lockdown.domain.account.Account;
import com.lockdown.domain.account.BudgetedTransaction;
import com.lockdown.domain.account.SingleBudgetEntryMapping;
import com.lockdown.domain.account.Transactions;
import com.lockdown.domain.budget.Budget;
import com.lockdown.domain.budget.BudgetEntry;
import com.lockdown.domain.money.Money;
import com.lockdown.domain.portfolio.BudgetSnapshot;

public class BudgetSnapshotTest {

	@Test
	public void noAccountsEnsureNoSnapshots() {
		BudgetSnapshot snapshot = new BudgetSnapshot(Budget.empty(), new ArrayList<>());
		assertTrue(snapshot.getBudgetEntrySnapshots().isEmpty());
	}
	
	@Test
	public void oneAccountWithTransactionsForOneBudgetEntryEnsureCorrectEntrySnapshots() {
				
		BudgetEntry entry = BudgetEntry.blank();
		Budget budget = new Budget(null, List.of(entry));
		
		Account account = Account.blank()
			.addBudgetedTransaction(transactionFor(entry, 5))
			.addBudgetedTransaction(transactionFor(entry, -10))
			.addBudgetedTransaction(transactionFor(entry, 15));
		
		BudgetSnapshot snapshot = new BudgetSnapshot(budget, List.of(account));
		
		assertFalse(snapshot.getBudgetEntrySnapshots().isEmpty());
		assertEquals(Money.dollars(10), snapshot.getBudgetEntrySnapshots().get(entry).getBalance());
	}
	
	private static BudgetedTransaction transactionFor(BudgetEntry entry, long dollars) {
		return Transactions.budgetedForAmountWithMapping(Money.dollars(dollars), new SingleBudgetEntryMapping(entry));
	}
	
	@Test
	public void oneAccountWithTransactionsForTwoBudgetEntriesEnsureCorrectEntrySnapshots() {
				
		BudgetEntry entry1 = BudgetEntry.blank();
		BudgetEntry entry2 = BudgetEntry.blank();
		entry1.setName("Entry 1");
		entry2.setName("Entry 2");
		Budget budget = new Budget(null, List.of(entry1, entry2));
		
		Account account = Account.blank()
			.addBudgetedTransaction(transactionFor(entry1, 5))
			.addBudgetedTransaction(transactionFor(entry1, -10))
			.addBudgetedTransaction(transactionFor(entry2, 15))
			.addBudgetedTransaction(transactionFor(entry2, -5));
		
		BudgetSnapshot snapshot = new BudgetSnapshot(budget, List.of(account));
		
		assertFalse(snapshot.getBudgetEntrySnapshots().isEmpty());
		assertEquals(Money.dollars(-5), snapshot.getBudgetEntrySnapshots().get(entry1).getBalance());
		assertEquals(Money.dollars(10), snapshot.getBudgetEntrySnapshots().get(entry2).getBalance());
	}
	
	@Test
	public void twoAccountsWithTransactionsForTwoBudgetEntriesEnsureCorrectEntrySnapshots() {
				
		BudgetEntry entry1 = BudgetEntry.blank();
		BudgetEntry entry2 = BudgetEntry.blank();
		entry1.setName("Entry 1");
		entry2.setName("Entry 2");
		Budget budget = new Budget(null, List.of(entry1, entry2));
		
		Account account1 = Account.blank()
			.addBudgetedTransaction(transactionFor(entry1, 50))
			.addBudgetedTransaction(transactionFor(entry1, -10))
			.addBudgetedTransaction(transactionFor(entry2, 30))
			.addBudgetedTransaction(transactionFor(entry2, -20));
		
		Account account2 = Account.blank()
			.addBudgetedTransaction(transactionFor(entry1, 20))
			.addBudgetedTransaction(transactionFor(entry1, -40))
			.addBudgetedTransaction(transactionFor(entry2, 10))
			.addBudgetedTransaction(transactionFor(entry2, -30));
		
		BudgetSnapshot snapshot = new BudgetSnapshot(budget, List.of(account1, account2));
		
		assertFalse(snapshot.getBudgetEntrySnapshots().isEmpty());
		assertEquals(Money.dollars(20), snapshot.getBudgetEntrySnapshots().get(entry1).getBalance());
		assertEquals(Money.dollars(-10), snapshot.getBudgetEntrySnapshots().get(entry2).getBalance());
	}
}
