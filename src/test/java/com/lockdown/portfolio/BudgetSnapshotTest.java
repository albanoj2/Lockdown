package com.lockdown.portfolio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.lockdown.account.Account;
import com.lockdown.account.SingleBudgetEntryMapping;
import com.lockdown.account.Transaction;
import com.lockdown.budget.Budget;
import com.lockdown.budget.BudgetEntry;
import com.lockdown.money.Money;

public class BudgetSnapshotTest {

	@Test
	public void noAccountsEnsureNoSnapshots() {
		BudgetSnapshot snapshot = new BudgetSnapshot(Budget.empty(), new ArrayList<>());
		assertTrue(snapshot.getBudgetEntrySnapshots().isEmpty());
	}
	
	@Test
	public void oneAccountWithTransactionsForOneBudgetEntryEnsureCorrectEntrySnapshots() {
				
		BudgetEntry entry = BudgetEntry.blank();
		Budget budget = new Budget(List.of(entry));
		
		Account account = Account.blank()
			.addTransaction(transactionFor(entry, 5))
			.addTransaction(transactionFor(entry, -10))
			.addTransaction(transactionFor(entry, 15));
		
		BudgetSnapshot snapshot = new BudgetSnapshot(budget, List.of(account));
		
		assertFalse(snapshot.getBudgetEntrySnapshots().isEmpty());
		assertEquals(Money.dollars(10), snapshot.getBudgetEntrySnapshots().get(entry).getBalance());
	}
	
	private static Transaction transactionFor(BudgetEntry entry, long dollars) {
		return Transaction.now(Money.dollars(dollars), new SingleBudgetEntryMapping(entry));
	}
	
	@Test
	public void oneAccountWithTransactionsForTwoBudgetEntriesEnsureCorrectEntrySnapshots() {
				
		BudgetEntry entry1 = BudgetEntry.blank();
		BudgetEntry entry2 = BudgetEntry.blank();
		entry1.setName("Entry 1");
		entry2.setName("Entry 2");
		Budget budget = new Budget(List.of(entry1, entry2));
		
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
				
		BudgetEntry entry1 = BudgetEntry.blank();
		BudgetEntry entry2 = BudgetEntry.blank();
		entry1.setName("Entry 1");
		entry2.setName("Entry 2");
		Budget budget = new Budget(List.of(entry1, entry2));
		
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
