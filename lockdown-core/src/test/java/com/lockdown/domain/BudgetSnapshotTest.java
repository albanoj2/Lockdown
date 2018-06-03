package com.lockdown.domain;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BudgetSnapshotTest {

	@Test
	public void noAccountsEnsureNoSnapshots() {
		BudgetSnapshot snapshot = new BudgetSnapshot(Budget.empty(), new ArrayList<>());
		assertTrue(snapshot.getBudgetItemSnapshotsMap().isEmpty());
	}
	
	@Test
	public void oneAccountWithTransactionsForOneBudgetEntryEnsureCorrectEntrySnapshots() {
				
		BudgetItem entry = BudgetItem.blank();
		Budget budget = new Budget(null, "foo", "", List.of(entry));
		
		Account account = Account.blank()
			.addTransaction(transactionFor(entry, 5))
			.addTransaction(transactionFor(entry, -10))
			.addTransaction(transactionFor(entry, 15));
		
		BudgetSnapshot snapshot = new BudgetSnapshot(budget, List.of(account));
		
		assertFalse(snapshot.getBudgetItemSnapshotsMap().isEmpty());
		assertEquals(Money.dollars(10), snapshot.getBudgetItemSnapshotsMap().get(entry).getBalance());
	}
	
	private static Transaction transactionFor(BudgetItem item, long dollars) {
		return Transactions.budgetedForAmountWithMapping(Money.dollars(dollars), BudgetItemMapping.withMapping(item, Money.dollars(dollars)));
	}
	
	@Test
	public void oneAccountWithTransactionsForTwoBudgetEntriesEnsureCorrectEntrySnapshots() {
				
		BudgetItem entry1 = BudgetItem.withName("Entry 1");
		BudgetItem entry2 = BudgetItem.withName("Entry 2");
		Budget budget = new Budget(null, "foo", "", List.of(entry1, entry2));
		
		Account account = Account.blank()
			.addTransaction(transactionFor(entry1, 5))
			.addTransaction(transactionFor(entry1, -10))
			.addTransaction(transactionFor(entry2, 15))
			.addTransaction(transactionFor(entry2, -5));
		
		BudgetSnapshot snapshot = new BudgetSnapshot(budget, List.of(account));
		
		assertFalse(snapshot.getBudgetItemSnapshotsMap().isEmpty());
		assertEquals(Money.dollars(-5), snapshot.getBudgetItemSnapshotsMap().get(entry1).getBalance());
		assertEquals(Money.dollars(10), snapshot.getBudgetItemSnapshotsMap().get(entry2).getBalance());
	}
	
	@Test
	public void twoAccountsWithTransactionsForTwoBudgetEntriesEnsureCorrectEntrySnapshots() {
				
		BudgetItem entry1 = BudgetItem.withName("Entry 1");
		BudgetItem entry2 = BudgetItem.withName("Entry 2");
		Budget budget = new Budget(null, "foo", "", List.of(entry1, entry2));
		
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
		
		assertFalse(snapshot.getBudgetItemSnapshotsMap().isEmpty());
		assertEquals(Money.dollars(20), snapshot.getBudgetItemSnapshotsMap().get(entry1).getBalance());
		assertEquals(Money.dollars(-10), snapshot.getBudgetItemSnapshotsMap().get(entry2).getBalance());
	}
	
	@Test
	public void createBlankBudgetSnapshotEnsureBudgetExistsAndNoAccountsPresent() {
		BudgetSnapshot snapshot = new BudgetSnapshot();
		assertNotNull(snapshot.getBudget());
		assertTrue(snapshot.getAccounts().isEmpty());
	}
}
