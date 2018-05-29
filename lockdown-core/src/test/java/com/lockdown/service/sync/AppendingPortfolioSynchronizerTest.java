package com.lockdown.service.sync;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.lockdown.domain.Account;
import com.lockdown.domain.Delta;
import com.lockdown.domain.SynchronizationLogEntry;
import com.lockdown.persist.store.AccountDataStore;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.DiscoveredTransaction;

public class AppendingPortfolioSynchronizerTest {

	private AccountDataStore accountDataStore;
	private AppendingPortfolioSynchronizer synchronizer;
	private SynchronizationLogEntry logEntry;
	
	@Before
	public void setUp() {
		accountDataStore = Mockito.mock(AccountDataStore.class);
		synchronizer = new AppendingPortfolioSynchronizer(accountDataStore);
		logEntry = Mockito.mock(SynchronizationLogEntry.class);
	}
	
	@Test(expected = NullPointerException.class)
	public void synchronizeWithNullDiscoveredAccountsEnsureNullPointerExceptionThrown() {
		synchronizer.synchronize(null, new ArrayList<>(), logEntry);
	}
	
	@Test(expected = NullPointerException.class)
	public void synchronizeWithNullDiscoveredTransactionsEnsureNullPointerExceptionThrown() {
		synchronizer.synchronize(new ArrayList<>(), null, logEntry);
	}
	
	@Test(expected = NullPointerException.class)
	public void synchronizeWithNullLogEntryEnsureNullPointerExceptionThrown() {
		synchronizer.synchronize(new ArrayList<>(), new ArrayList<>(), null);
	}
	
	@Test
	public void synchronizeWithNoDiscoveredAccountsEnsureNoAccountsAdded() {
		ensureWillAddDiscoveredAccounts();
		synchronizer.synchronize(new ArrayList<>(), new ArrayList<>(), logEntry);
		assertNoAccountsAdded();
		assertAccountsAddedNotIncremented();
	}
	
	private void ensureWillAddDiscoveredAccounts() {
		doReturn(false).when(accountDataStore).existsById(any(String.class));
	}
	
	private void assertNoAccountsAdded() {
		verify(accountDataStore, never()).saveAndCascade(any(Account.class));
		verify(accountDataStore, never()).save(any(Account.class));
	}
	
	private void assertAccountsAddedNotIncremented() {
		verify(logEntry, never()).incrementAccountsAdded();
	}
	
	@Test
	public void synchronizeWithOneDiscoveredAccountEnsureOneAccountsAdded() {
		DiscoveredAccount accountToAdd = discoveredAccountWithKey("foo");
		ensureWillAddDiscoveredAccounts();
		synchronizer.synchronize(List.of(accountToAdd), new ArrayList<>(), logEntry);
		assertAccountsAdded(1);
		assertNumberOfAddedAccountsIncremented();
	}
	
	private static DiscoveredAccount discoveredAccountWithKey(String key) {
		return new DiscoveredAccount(key, "someName", "someType", "someSubType");
	}
	
	private void assertAccountsAdded(int count) {
		verify(accountDataStore, times(count)).saveAndCascade(any(Account.class));
	}
	
	private void assertNumberOfAddedAccountsIncremented() {
		verify(logEntry, times(1)).incrementAccountsAdded();
	}
	
	@Test
	public void synchronizeWithOneDiscoveredAccountThatExistsEnsureAddedAccountsNotIncremented() {
		String key = "foo";
		DiscoveredAccount accountToAdd = discoveredAccountWithKey(key);
		ensureAccountAlreadyExistsWithKey(key);
		synchronizer.synchronize(List.of(accountToAdd), new ArrayList<>(), logEntry);
		assertNoAccountsAdded();
		assertNumberOfAddedAccountsNotIncremented();
	}
	
	private void ensureAccountAlreadyExistsWithKey(String key) {
		doReturn(true).when(accountDataStore).existsByKey(eq(key));
	}
	
	private void assertNumberOfAddedAccountsNotIncremented() {
		verify(logEntry, never()).incrementAccountsAdded();
	}
	
	@Test
	public void synchronizeWithNoDiscoveredTransactionsEnsureNoTransactionsAdded() {
		Account account = accountWithKey("foo");
		configureWillAddDiscoveredTransactions(account);
		synchronizer.synchronize(new ArrayList<>(), new ArrayList<>(), logEntry);
		assertNoTransactionsAdded(account);
		assertTransactionsAddedNotIncremented();
		assertTransactionsUpdatedNotIncremented();
	}
	
	private static Account accountWithKey(String key) {
		Account account = Mockito.mock(Account.class);
		doReturn(key).when(account).getKey();
		return account;
	}
	
	private void configureWillAddDiscoveredTransactions(Account account) {
		doReturn(Delta.ADDED).when(account).addTransactionOrUpdateIfExists(any(), any());
		when(accountDataStore.findByKey(eq(account.getKey()))).thenReturn(Optional.of(account));
	}
	
	private static void assertNoTransactionsAdded(Account account) {
		assertTransactionsAdded(account, 0);
	}
	
	private static void assertTransactionsAdded(Account account, int number) {
		verify(account, times(number)).addTransactionOrUpdateIfExists(any(), any());
	}

	private void assertTransactionsAddedNotIncremented() {
		verify(logEntry, never()).incrementTransactionsAdded();
	}
	
	private void assertTransactionsUpdatedNotIncremented() {
		verify(logEntry, never()).incrementTransactionsUpdated();
	}
	
	@Test
	public void synchronizeWithOneDiscoveredTransactionToBeAddedEnsureOneTransactionsAdded() {
		Account account = accountWithKey("foo");
		configureWillAddDiscoveredTransactions(account);
		DiscoveredTransaction transaction = discoveredTransactionWithAccountKey(account.getKey());
		synchronizer.synchronize(new ArrayList<>(), List.of(transaction), logEntry);
		assertTransactionsAdded(account, 1);
		assertTransactionsAddedIncremented();
		assertTransactionsUpdatedNotIncremented();
	}
	
	private static DiscoveredTransaction discoveredTransactionWithAccountKey(String key) {
		DiscoveredTransaction transaction = Mockito.mock(DiscoveredTransaction.class);
		doReturn(key).when(transaction).getAccountKey();
		return transaction;
	}
	
	private void assertTransactionsAddedIncremented() {
		verify(logEntry, times(1)).incrementTransactionsAdded();
	}
	
	@Test
	public void synchronizeWithOneDiscoveredTransactionToBeUpdatedEnsureOneTransactionsUpdated() {
		Account account = accountWithKey("foo");
		configureWillUpdateDiscoveredTransactions(account);
		DiscoveredTransaction transaction = discoveredTransactionWithAccountKey(account.getKey());
		synchronizer.synchronize(new ArrayList<>(), List.of(transaction), logEntry);
		assertTransactionsAdded(account, 1);
		assertTransactionsAddedNotIncremented();
		assertTransactionsUpdatedIncremented();
	}
	
	private void configureWillUpdateDiscoveredTransactions(Account account) {
		doReturn(Delta.UPDATED).when(account).addTransactionOrUpdateIfExists(any(), any());
		when(accountDataStore.findByKey(eq(account.getKey()))).thenReturn(Optional.of(account));
	}
	
	private void assertTransactionsUpdatedIncremented() {
		verify(logEntry, times(1)).incrementTransactionsUpdated();
	}
	
	@Test
	public void synchronizeWithDiscoveredTransactionsThatHasNoAssociatedAccountEnsureNoTransactionsAddedOrUpdated() {
		String accountKey = "foo";
		configureNoAccountFor(accountKey);
		DiscoveredTransaction transaction = discoveredTransactionWithAccountKey(accountKey);
		synchronizer.synchronize(new ArrayList<>(), List.of(transaction), logEntry);
		assertTransactionsAddedNotIncremented();
		assertTransactionsUpdatedNotIncremented();
	}
	
	private void configureNoAccountFor(String accountKey) {
		doReturn(Optional.empty()).when(accountDataStore).findByKey(eq(accountKey));
	}
}
