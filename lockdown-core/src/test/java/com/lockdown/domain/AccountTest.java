package com.lockdown.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.lockdown.domain.Account;
import com.lockdown.domain.Money;
import com.lockdown.domain.Transaction;

public class AccountTest {

	private Account account;
	
	@Before
	public void setUp() {
		this.account = Account.blank();
	}
	
	@Test
	public void noBudgetedTransactionsEnsureZeroBudgetedBalance() {
		assertAccountBudgetedBalanceIsZero();
	}
	
	private void assertAccountBudgetedBalanceIsZero() {
		assertAccountBudgetedBalanceIs(Money.zero());
	}
	
	private void assertAccountBudgetedBalanceIs(Money amount) {
		assertEquals(account.getBudgetedBalance(), amount);
	}
	
	@Test
	public void oneBudgetedTransactionWithZeroValueEnsureZeroBudgetedBalance() {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.zero());
		account.addTransaction(budgetedTransaction);
		assertAccountBudgetedBalanceIsZero();
	}
	
	@Test
	public void oneBudgetedTransactionWithPositiveValueEnsureCorrectBudgetedBalance() {
		addBudgetedTransactionWithAmount(1);
		assertAccountBudgetedBalanceIs(Money.cents(1));
	}
	
	private void addBudgetedTransactionWithAmount(long amount) {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.cents(amount));
		account.addTransaction(budgetedTransaction);
	}
	
	@Test
	public void twoBudetedTransactionsWithPositiveValuesEnsureCorrectBudgetedBalance() {
		addBudgetedTransactionWithAmount(1);
		addBudgetedTransactionWithAmount(20);
		assertAccountBudgetedBalanceIs(Money.cents(21));
	}
	
	@Test
	public void twoBudgetedTransactionsWithNegativeValuesEnsureCorrectBudgetedBalance() {
		addBudgetedTransactionWithAmount(-1);
		addBudgetedTransactionWithAmount(-20);
		assertAccountBudgetedBalanceIs(Money.cents(-21));
	}
	
	@Test
	public void twoBudgetedTransactionsWithOneNegativeAndOnePositiveValueEnsureCorrectBudgetedBalance() {
		addBudgetedTransactionWithAmount(-1);
		addBudgetedTransactionWithAmount(20);
		assertAccountBudgetedBalanceIs(Money.cents(19));
	}
	
	@Test
	public void noUnbudgetedTransactionsEnsureZeroUnbudgetedBalance() {
		assertAccountUnbudgetedBalanceIsZero();
	}
	
	private void assertAccountUnbudgetedBalanceIsZero() {
		assertAccountUnbudgetedBalanceIs(Money.zero());
	}
	
	private void assertAccountUnbudgetedBalanceIs(Money amount) {
		assertEquals(account.getUnbudgetedBalance(), amount);
	}
	
	@Test
	public void oneUnbudgetedTransactionWithZeroValueEnsureZeroUnbudgetedBalance() {
		Transaction unbudgetedTransaction = Transactions.unbudgetedForAmount(Money.zero());
		account.addTransaction(unbudgetedTransaction);
		assertAccountUnbudgetedBalanceIsZero();
	}
	
	@Test
	public void oneUnbudgetedTransactionWithPositiveValueEnsureCorrectUnbudgetedBalance() {
		addUnbudgetedTransactionWithAmount(1);
		assertAccountUnbudgetedBalanceIs(Money.cents(1));
	}
	
	private void addUnbudgetedTransactionWithAmount(long amount) {
		Transaction unbudgetedTransaction = Transactions.unbudgetedForAmount(Money.cents(amount));
		account.addTransaction(unbudgetedTransaction);
	}
	
	@Test
	public void twoBudetedTransactionsWithPositiveValuesEnsureCorrectUnbudgetedBalance() {
		addUnbudgetedTransactionWithAmount(1);
		addUnbudgetedTransactionWithAmount(20);
		assertAccountUnbudgetedBalanceIs(Money.cents(21));
	}
	
	@Test
	public void twoUnbudgetedTransactionsWithNegativeValuesEnsureCorrectUnbudgetedBalance() {
		addUnbudgetedTransactionWithAmount(-1);
		addUnbudgetedTransactionWithAmount(-20);
		assertAccountUnbudgetedBalanceIs(Money.cents(-21));
	}
	
	@Test
	public void twoUnbudgetedTransactionsWithOneNegativeAndOnePositiveValueEnsureCorrectUnbudgetedBalance() {
		addUnbudgetedTransactionWithAmount(-1);
		addUnbudgetedTransactionWithAmount(20);
		assertAccountUnbudgetedBalanceIs(Money.cents(19));
	}
	
//	@Test
//	public void twoEqualTransactionsAddOrReplaceIfExistsEnsureTransactionReplaced() {
//		String commonKey = "foo";
//		Transaction existing = Transactions.withKey(commonKey);
//		Transaction replacement = Transactions.withKey(commonKey);
//		
//		account.addTransaction(existing);
//		assertAccountContains(existing);
//		assertAccountDoesNotContain(replacement);
//		
//		account.addTransactionOrUpdateIfExists(replacement);
//		assertAccountContains(replacement);
//		assertAccountDoesNotContain(existing);
//	}
	
	private void assertAccountContains(Transaction transaction) {
		assertTrue(accountContains(transaction));
	}
	
	private boolean accountContains(Transaction transaction) {
		return account.getTransactions().stream()
			.filter(t -> t == transaction)
			.findFirst()
			.isPresent();
	}
	
	private void assertAccountDoesNotContain(Transaction transaction) {
		assertFalse(accountContains(transaction));
	}
	
	@Test
	public void twoUnEqualTransactionsAddOrReplaceIfExistsEnsureTransactionAdded() {
		Transaction existing = Transactions.withKey("foo");
		Transaction different = Transactions.withKey("bar");
		
		account.addTransaction(existing);
		assertAccountContains(existing);
		assertAccountDoesNotContain(different);
		
		account.addTransactionOrUpdateIfExists(different);
		assertAccountContains(existing);
		assertAccountContains(different);
	}
	
//	@Test
//	public void twoUnEqualTransactionsAddOrReplaceIfExistsEnsureIdAndKeyAreUnchanged() {
//		String commonKey = "foo";
//		Transaction existing = Transactions.withIdAndKey("foo", commonKey);
//		Transaction replacement = Transactions.withIdAndKey("bar", commonKey);
//		
//		account.addTransaction(existing);
//		account.addTransactionOrUpdateIfExists(replacement);
//		
//		assertAccountContains(replacement);
//		assertEquals(existing.getId(), account.getTransactions().get(0).getId());
//		assertEquals(existing.getKey(), account.getTransactions().get(0).getKey());
//	}
}
