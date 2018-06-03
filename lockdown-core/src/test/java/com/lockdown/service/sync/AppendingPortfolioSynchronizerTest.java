package com.lockdown.service.sync;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.atLeast;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.lockdown.domain.Account;
import com.lockdown.domain.Account.Subtype;
import com.lockdown.domain.Account.Type;
import com.lockdown.domain.Delta;
import com.lockdown.domain.Institution;
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
		return new DiscoveredAccount(key, "someName", Institution.UNKNOWN, Type.OTHER, Subtype.OTHER);
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
		assertAccountNotSavedAndCascaded(account);
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
	
	@SuppressWarnings("unchecked")
	private void assertAccountNotSavedAndCascaded(Account account) {
		verify(accountDataStore, never()).saveAndCascade(eq(account));
		
		ArgumentCaptor<List<Account>> multiSaveCaptor = ArgumentCaptor.forClass(List.class);
		verify(accountDataStore, atLeast(0)).saveAllAndCascade(multiSaveCaptor.capture());
		List<List<Account>> savedMultiAccount = multiSaveCaptor.getAllValues();
		
		if (!savedMultiAccount.isEmpty()) {
			assertTrue(savedMultiAccount.get(0).isEmpty());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void assertAccountSavedAndCascaded(Account account) {
		ArgumentCaptor<Account> singleSaveCaptor = ArgumentCaptor.forClass(Account.class);
		verify(accountDataStore, atLeast(0)).saveAndCascade(singleSaveCaptor.capture());
		
		ArgumentCaptor<List<Account>> multiSaveCaptor = ArgumentCaptor.forClass(List.class);
		verify(accountDataStore, atLeast(0)).saveAllAndCascade(multiSaveCaptor.capture());
		
		List<Account> savedSingleAccount = singleSaveCaptor.getAllValues();
		List<List<Account>> savedMultiAccount = multiSaveCaptor.getAllValues();
		
		assertTrue(savedSingleAccount.size() == 1 || savedMultiAccount.size() == 1);
		
		if (!savedSingleAccount.isEmpty()) {
			assertEquals(account, savedSingleAccount.get(0));
		}
		else if (!savedMultiAccount.isEmpty()) {
			assertTrue(savedMultiAccount.get(0).contains(account));
		}
		else {
			fail("Should never reach here");
		}
	}
	
//	ArgumentCaptor<String> propertyKeyCaptor = ArgumentCaptor.forClass(String.class);
//	Mockito.verify(foo, atLeast(0)).getProperty(propertyKeyCaptor.capture(), anyString());
//
//	ArgumentCaptor<String> propertyKeyCaptor2 = ArgumentCaptor.forClass(String.class);
//	Mockito.verify(foo, atLeast(0)).getProperty(propertyKeyCaptor2.capture());
//
//	List<String> propertyKeyValues = propertyKeyCaptor.getAllValues();
//	List<String> propertyKeyValues2 = propertyKeyCaptor2.getAllValues();
//
//	assertTrue(!propertyKeyValues.isEmpty() || !propertyKeyValues2.isEmpty());
	
	@Test
	public void synchronizeWithOneDiscoveredTransactionToBeAddedEnsureOneTransactionsAdded() {
		Account account = accountWithKey("foo");
		configureWillAddDiscoveredTransactions(account);
		DiscoveredTransaction transaction = discoveredTransactionWithAccountKey(account.getKey());
		synchronizer.synchronize(new ArrayList<>(), List.of(transaction), logEntry);
		assertTransactionsAdded(account, 1);
		assertTransactionsAddedIncremented();
		assertTransactionsUpdatedNotIncremented();
		assertAccountSavedAndCascaded(account);
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
		assertAccountSavedAndCascaded(account);
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
		assertNoAccountSavedAndCascaded();
	}
	
	private void configureNoAccountFor(String accountKey) {
		doReturn(Optional.empty()).when(accountDataStore).findByKey(eq(accountKey));
	}
	
	private void assertNoAccountSavedAndCascaded() {
		verify(accountDataStore, never()).saveAndCascade(any(Account.class));
	}
}
