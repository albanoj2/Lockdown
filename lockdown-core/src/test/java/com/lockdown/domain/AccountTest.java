package com.lockdown.domain;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.lockdown.domain.Account.Subtype;
import com.lockdown.domain.Account.Type;

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
	
	private Transaction addBudgetedTransactionWithAmount(long amount) {
		Transaction budgetedTransaction = Transactions.budgetedForAmount(Money.cents(amount));
		account.addTransaction(budgetedTransaction);
		return budgetedTransaction;
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
	
	private Transaction addUnbudgetedTransactionWithAmount(long amount) {
		Transaction unbudgetedTransaction = Transactions.unbudgetedForAmount(Money.cents(amount));
		account.addTransaction(unbudgetedTransaction);
		return unbudgetedTransaction;
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
	public void givenNoTransactionsEnsureBudgetedTransactionsCorrect() {
		assertEquals(0, account.getTransactionCount());
		assertTrue(account.getBudgetedTransactions().isEmpty());
	}
	
	@Test
	public void givenNoTransactionsEnsureUnbudgetedTransactionsCorrect() {
		assertEquals(0, account.getTransactionCount());
		assertTrue(account.getUnbudgetedTransactions().isEmpty());
	}
	
	@Test
	public void givenOneBudgetedAndOneUnbudgetedTransactionEnsureBudgetedTransactionsCorrect() {
		Transaction budgetedTransaction = addBudgetedTransactionWithAmount(2);
		addUnbudgetedTransactionWithAmount(4);
		assertEquals(2, account.getTransactionCount());
		assertEquals(1, account.getBudgetedTransactions().size());
		assertEquals(budgetedTransaction, account.getBudgetedTransactions().get(0));
	}
	
	@Test
	public void givenOneBudgetedAndOneUnbudgetedTransactionEnsureUnbudgetedTransactionsCorrect() {
		addBudgetedTransactionWithAmount(2);
		Transaction unbudgetedTransaction = addUnbudgetedTransactionWithAmount(4);
		assertEquals(2, account.getTransactionCount());
		assertEquals(1, account.getUnbudgetedTransactions().size());
		assertEquals(unbudgetedTransaction, account.getUnbudgetedTransactions().get(0));
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
		
		Delta delta = account.addTransactionOrUpdateIfExists(existingTransaction.getKey(), updatedBody);
		Transaction updatedTransaction = account.getTransactions().get(0);
		
		assertEquals(idBeforeUpdate, updatedTransaction.getId());
		assertEquals(keyBeforeUpdate, updatedTransaction.getKey());
		assertBodyMatchesTransaction(updatedBody, updatedTransaction);
		assertEquals(mappingBeforeUpdate, updatedTransaction.getBudgetItemMapping().orElse(null));
		assertEquals(Delta.UPDATED, delta);
	}
	
	private static void assertBodyDoesNotMatchTransaction(Transaction transaction, TransactionBody body) {
		assertFalse(transaction.bodyEquals(body));
	}
	
	@Test
	public void addOrUpdateWithExistingKeyWithMatchingBodyEnsureTransactionBodyUpdated() {
		Transaction existingTransaction = Transactions.budgetedForAmount(Money.dollars(200));
		String idBeforeUpdate = existingTransaction.getId();
		String keyBeforeUpdate = existingTransaction.getKey();
		BudgetItemMapping mappingBeforeUpdate = existingTransaction.getBudgetItemMapping().orElse(null);
		
		account.addTransaction(existingTransaction);
		
		TransactionBody updatedBody = existingTransaction.getBody();
		assertBodyMatchesTransaction(existingTransaction, updatedBody);
		
		Delta delta = account.addTransactionOrUpdateIfExists(existingTransaction.getKey(), updatedBody);
		Transaction updatedTransaction = account.getTransactions().get(0);
		
		assertEquals(idBeforeUpdate, updatedTransaction.getId());
		assertEquals(keyBeforeUpdate, updatedTransaction.getKey());
		assertBodyMatchesTransaction(updatedBody, updatedTransaction);
		assertEquals(mappingBeforeUpdate, updatedTransaction.getBudgetItemMapping().orElse(null));
		assertEquals(Delta.UNCHANGED, delta);
	}
	
	private static void assertBodyMatchesTransaction(Transaction transaction, TransactionBody body) {
		assertTrue(transaction.bodyEquals(body));
	}
	
	@Test
	public void givenNoTransactionsWhenAddThenRemoveSameTransactionEnsureNoTransactions() {
		Transaction transaction = transactionWithKey("foo");
		account.addTransaction(transaction);
		assertTrue(account.containsTransaction(transaction));
		account.removeTransaction(transaction);
		assertFalse(account.containsTransaction(transaction));
	}
	
	private static Transaction transactionWithKey(String key) {
		return new Transaction("someId", key, TransactionBody.empty(), Optional.empty(), Optional.empty());
	}
	
	@Test
	public void givenExistingTransactionWhenGetTransactionByIdEnsureCorrectTransactionFound() {
		String id = "foo";
		Transaction transaction = transactionWithId(id);
		account.addTransaction(transaction);
		Optional<Transaction> foundTransaction = account.getTransactionById(id);
		assertTrue(foundTransaction.isPresent());
		assertEquals(transaction, foundTransaction.get());
	}
	
	private static Transaction transactionWithId(String id) {
		return new Transaction(id, "foo", TransactionBody.empty(), Optional.empty(), Optional.empty());
	}
	
	@Test
	public void givenValidAccountEnsureAccountEqualToItself() {
		Account account = accountWithKey("foo");
		assertEquals(account, account);
	}
	
	private static Account accountWithKey(String key) {
		return new Account("someId", key, "someName", Institution.UNKNOWN, Type.UNKNOWN, Subtype.UNKNOWN, Collections.emptyList());
	}
	
	@Test
	public void givenValidAccountEnsureAccountNotEqualToNonAccountObject() {
		Account account = accountWithKey("foo");
		assertNotEquals(account, new Object());
	}
	
	@Test
	public void givenTwoAccountsWithSameKeyEnsureAccountsEqual() {
		String commonKey = "foo";
		Account account1 = accountWithKey(commonKey);
		Account account2 = accountWithKey(commonKey);
		assertEquals(account1, account2);
	}
	
	@Test
	public void givenTwoAccountsWithDifferentKeysEnsureAccountsNotEqual() {
		Account account1 = accountWithKey("foo");
		Account account2 = accountWithKey("bar");
		assertNotEquals(account1, account2);
	}
	
	@Test
	public void givenTwoAccountsWithSameKeyEnsureAccountsHaveSameHash() {
		String commonKey = "foo";
		Account account1 = accountWithKey(commonKey);
		Account account2 = accountWithKey(commonKey);
		assertEquals(account1.hashCode(), account2.hashCode());
	}
	
	@Test
	public void givenTwoAccountsWithDifferentKeysEnsureAccountsDoNotHaveSameHash() {
		Account account1 = accountWithKey("foo");
		Account account2 = accountWithKey("bar");
		assertNotEquals(account1.hashCode(), account2.hashCode());
	}
	
	@Test
	public void givenEmptyAccountWhenGetUnbdugetedTransactionCountThenZero() {
		assertEquals(0, account.getUnbudgetedTransactionsCount());
	}
	
	@Test
	public void givenAccountWithOneUnbudgetedTransactionWhenGetUnbdugetedTransactionCountThenOne() {
		addUnbudgetedTransactionWithAmount(1);
		assertEquals(1, account.getUnbudgetedTransactionsCount());
	}
	
	@Test
	public void givenAccountWithOneBudgetedAndOneUnbudgetedTransactionWhenGetUnbdugetedTransactionCountThenOne() {
		addUnbudgetedTransactionWithAmount(1);
		addBudgetedTransactionWithAmount(2);
		assertEquals(1, account.getUnbudgetedTransactionsCount());
	}
}
