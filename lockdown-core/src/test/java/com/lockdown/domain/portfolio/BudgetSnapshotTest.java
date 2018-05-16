package com.lockdown.domain.portfolio;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lockdown.domain.Account;
import com.lockdown.domain.Budget;
import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.BudgetSnapshot;
import com.lockdown.domain.Money;
import com.lockdown.domain.SingleBudgetItemMapping;
import com.lockdown.domain.Transaction;
import com.lockdown.domain.account.Transactions;

public class BudgetSnapshotTest {

	@Test
	public void noAccountsEnsureNoSnapshots() {
		BudgetSnapshot snapshot = new BudgetSnapshot(Budget.empty(), new ArrayList<>());
		assertTrue(snapshot.getBudgetEntrySnapshots().isEmpty());
	}
	
	@Test
	public void oneAccountWithTransactionsForOneBudgetEntryEnsureCorrectEntrySnapshots() {
				
		BudgetItem entry = BudgetItem.blank();
		Budget budget = new Budget(null, List.of(entry));
		
		Account account = Account.blank()
			.addTransaction(transactionFor(entry, 5))
			.addTransaction(transactionFor(entry, -10))
			.addTransaction(transactionFor(entry, 15));
		
		BudgetSnapshot snapshot = new BudgetSnapshot(budget, List.of(account));
		
		assertFalse(snapshot.getBudgetEntrySnapshots().isEmpty());
		assertEquals(Money.dollars(10), snapshot.getBudgetEntrySnapshots().get(entry).getBalance());
	}
	
	private static Transaction transactionFor(BudgetItem entry, long dollars) {
		return Transactions.budgetedForAmountWithMapping(Money.dollars(dollars), new SingleBudgetItemMapping(null, entry));
	}
	
	@Test
	public void oneAccountWithTransactionsForTwoBudgetEntriesEnsureCorrectEntrySnapshots() {
				
		BudgetItem entry1 = BudgetItem.blank();
		BudgetItem entry2 = BudgetItem.blank();
		entry1.setName("Entry 1");
		entry2.setName("Entry 2");
		Budget budget = new Budget(null, List.of(entry1, entry2));
		
		Account account = Account.blank()
			.addTransaction(transactionFor(entry1, 5))
			.addTransaction(transactionFor(entry1, -10))
			.addTransaction(transactionFor(entry2, 15))
			.addTransaction(transactionFor(entry2, -5));
		
		BudgetSnapshot snapshot = new BudgetSnapshot(budget, List.of(account));
		
		assertFalse(snapshot.getBudgetEntrySnapshots().isEmpty());
		assertEquals(Money.dollars(-5), snapshot.getBudgetEntrySnapshots().get(entry1).getBalance());
		assertEquals(Money.dollars(10), snapshot.getBudgetEntrySnapshots().get(entry2).getBalance());
	}
	
	@Test
	public void twoAccountsWithTransactionsForTwoBudgetEntriesEnsureCorrectEntrySnapshots() {
				
		BudgetItem entry1 = BudgetItem.blank();
		BudgetItem entry2 = BudgetItem.blank();
		entry1.setName("Entry 1");
		entry2.setName("Entry 2");
		Budget budget = new Budget(null, List.of(entry1, entry2));
		
		Account account1 = Account.blank()
			.addTransaction(transactionFor(entry1, 50))
			.addTransaction(transactionFor(entry1, -10))
			.addTransaction(transactionFor(entry2, 30))
			.addTransaction(transactionFor(entry2, -20));
		
		Account account2 = Account.blank()
			.addTransaction(transactionFor(entry1, 20))
			.addTransaction(transactionFor(entry1, -40))
			.addTransaction(transactionFor(entry2, 10))
			.addTransaction(transactionFor(entry2, -30));
		
		BudgetSnapshot snapshot = new BudgetSnapshot(budget, List.of(account1, account2));
		
		assertFalse(snapshot.getBudgetEntrySnapshots().isEmpty());
		assertEquals(Money.dollars(20), snapshot.getBudgetEntrySnapshots().get(entry1).getBalance());
		assertEquals(Money.dollars(-10), snapshot.getBudgetEntrySnapshots().get(entry2).getBalance());
	}
}
