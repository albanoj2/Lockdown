package com.lockdown.domain;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

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
	
	@Test
	public void addOrUpdateWithNonExistentKeyEnsureTransactionCreated() {
		String key = "foo";
		TransactionBody body = createTransactionBody();
		assertNoTransactions();
		account.addTransactionOrUpdateIfExists(key, body);
		
		assertEquals(1, account.getTransactionCount());
		Transaction createdTransaction = account.getTransactions().get(0);
		assertNull(createdTransaction.getId());
		assertEquals(key, createdTransaction.getKey());
		assertBodyMatchesTransaction(body, createdTransaction);
		assertFalse(createdTransaction.getBudgetItemMapping().isPresent());
	}

	private void assertBodyMatchesTransaction(TransactionBody body, Transaction createdTransaction) {
		assertEquals(body.getAmount(), createdTransaction.getAmount());
		assertEquals(body.getDate(), createdTransaction.getDate());
		assertEquals(body.getDescription(), createdTransaction.getDescription());
		assertEquals(body.getName(), createdTransaction.getName());
	}
	
	private static TransactionBody createTransactionBody() {
		return new TransactionBody(LocalDate.of(2000, 1, 20), Money.dollars(100), "A test body", "Some transaction", true);
	}

	private void assertNoTransactions() {
		assertEquals(0, account.getTransactionCount());
	}
	
	@Test
	public void addOrUpdateWithExistingKeyEnsureTransactionBodyUpdated() {
		Transaction existingTransaction = Transactions.budgetedForAmount(Money.dollars(200));
		String idBeforeUpdate = existingTransaction.getId();
		String keyBeforeUpdate = existingTransaction.getKey();
		BudgetItemMapping mappingBeforeUpdate = existingTransaction.getBudgetItemMapping().orElse(null);
		
		account.addTransaction(existingTransaction);
		
		TransactionBody updatedBody = createTransactionBody();
		assertBodyDoesNotMatchTransaction(existingTransaction, updatedBody);
		
		account.addTransactionOrUpdateIfExists(existingTransaction.getKey(), updatedBody);
		Transaction updatedTransaction = account.getTransactions().get(0);
		
		assertEquals(idBeforeUpdate, updatedTransaction.getId());
		assertEquals(keyBeforeUpdate, updatedTransaction.getKey());
		assertBodyMatchesTransaction(updatedBody, updatedTransaction);
		assertEquals(mappingBeforeUpdate, updatedTransaction.getBudgetItemMapping().orElse(null));
	}
	
	private static void assertBodyDoesNotMatchTransaction(Transaction transaction, TransactionBody body) {
		assertNotEquals(transaction.getAmount(), body.getAmount());
		assertNotEquals(transaction.getDate(), body.getDate());
		assertNotEquals(transaction.getName(), body.getName());
		assertNotEquals(transaction.getDescription(), body.getDescription());
		assertNotEquals(transaction.isPending(), body.isPending());
	}
	
}
