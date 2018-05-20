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
import com.lockdown.domain.Portfolio;
import com.lockdown.domain.SynchronizationLogEntry;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.DiscoveredTransaction;

public class AppendingPortfolioSynchronizerTest {

	private AppendingPortfolioSynchronizer synchronizer;
	private SynchronizationLogEntry logEntry;
	
	@Before
	public void setUp() {
		synchronizer = new AppendingPortfolioSynchronizer();
		logEntry = Mockito.mock(SynchronizationLogEntry.class);
	}
	
	@Test(expected = NullPointerException.class)
	public void synchronizeWithNullPortfolioEnsureNullPointerExceptionThrown() {
		synchronizer.synchronize(null, new ArrayList<>(), new ArrayList<>(), logEntry);
	}
	
	@Test(expected = NullPointerException.class)
	public void synchronizeWithNullDiscoveredAccountsEnsureNullPointerExceptionThrown() {
		synchronizer.synchronize(new Portfolio(), null, new ArrayList<>(), logEntry);
	}
	
	@Test(expected = NullPointerException.class)
	public void synchronizeWithNullDiscoveredTransactionsEnsureNullPointerExceptionThrown() {
		synchronizer.synchronize(new Portfolio(), new ArrayList<>(), null, logEntry);
	}
	
	@Test(expected = NullPointerException.class)
	public void synchronizeWithNullLogEntryEnsureNullPointerExceptionThrown() {
		synchronizer.synchronize(new Portfolio(), new ArrayList<>(), new ArrayList<>(), null);
	}
	
	@Test
	public void synchronizeWithNoDiscoveredAccountsEnsureNoAccountsAdded() {
		Portfolio portfolio = portfolioThatWillAddAccount();
		synchronizer.synchronize(portfolio, new ArrayList<>(), new ArrayList<>(), logEntry);
		assertNoAccountsAddedTo(portfolio);
		assertAccountsAddedNotIncremented();
	}
	
	private static Portfolio portfolioThatWillAddAccount() {
		Portfolio portfolio = Mockito.mock(Portfolio.class);
		when(portfolio.addAccountIfNotExists(any())).thenReturn(Delta.ADDED);
		return portfolio;
	}
	
	private static void assertNoAccountsAddedTo(Portfolio portfolio) {
		assertAccountsAddedTo(portfolio, 0);
	}
	
	private static void assertAccountsAddedTo(Portfolio portfolio, int numberOfAccounts) {
		verify(portfolio, times(numberOfAccounts)).addAccountIfNotExists(any());
	}
	
	private void assertAccountsAddedNotIncremented() {
		verify(logEntry, never()).incrementAccountsAdded();
	}
	
	@Test
	public void synchronizeWithOneDiscoveredAccountEnsureOneAccountsAdded() {
		DiscoveredAccount accountToAdd = Mockito.mock(DiscoveredAccount.class);
		Portfolio portfolio = portfolioThatWillAddAccount();
		synchronizer.synchronize(portfolio, List.of(accountToAdd), new ArrayList<>(), logEntry);
		assertAccountsAddedTo(portfolio, 1);
		assertNumberOfAddedAccountsIncremented();
	}
	
	private void assertNumberOfAddedAccountsIncremented() {
		verify(logEntry, times(1)).incrementAccountsAdded();
	}
	
	@Test
	public void synchronizeWithOneDiscoveredAccountThatExistsEnsureAddedAccountsNotIncremented() {
		DiscoveredAccount accountToAdd = Mockito.mock(DiscoveredAccount.class);
		Portfolio portfolio = portfolioThatWillNotAddAccount();
		synchronizer.synchronize(portfolio, List.of(accountToAdd), new ArrayList<>(), logEntry);
		assertAccountsAddedTo(portfolio, 1);
		assertNumberOfAddedAccountsNotIncremented();
	}
	
	private static Portfolio portfolioThatWillNotAddAccount() {
		Portfolio portfolio = Mockito.mock(Portfolio.class);
		when(portfolio.addAccountIfNotExists(any())).thenReturn(Delta.UNCHANGED);
		return portfolio;
	}
	
	private void assertNumberOfAddedAccountsNotIncremented() {
		verify(logEntry, never()).incrementAccountsAdded();
	}
	
	@Test
	public void synchronizeWithNoDiscoveredTransactionsEnsureNoTransactionsAdded() {
		String accountKey = "foo";
		Portfolio portfolio = portfolioThatWillAddTransactions(accountKey);
		synchronizer.synchronize(portfolio, new ArrayList<>(), new ArrayList<>(), logEntry);
		assertNoTransactionsAdded(portfolio.getAccountWithKey(accountKey).get());
		assertTransactionsAddedNotIncremented();
		assertTransactionsUpdatedNotIncremented();
	}
	
	private static Portfolio portfolioThatWillAddTransactions(String accountKey) {
		return portfolioThatWillOnAddTransaction(accountKey, Delta.ADDED);
	}
	
	private static Portfolio portfolioThatWillOnAddTransaction(String accountKey, Delta delta) {
		Portfolio portfolio = Mockito.mock(Portfolio.class);
		Account account = Mockito.mock(Account.class);
		doReturn(Optional.of(account)).when(portfolio).getAccountWithKey(eq(accountKey));
		doReturn(delta).when(account).addTransactionOrUpdateIfExists(any(), any());
		return portfolio;
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
		String accountKey = "foo";
		Portfolio portfolio = portfolioThatWillAddTransactions(accountKey);
		DiscoveredTransaction transaction = discoveredTransactionWithAccountKey(accountKey);
		synchronizer.synchronize(portfolio, new ArrayList<>(), List.of(transaction), logEntry);
		assertTransactionsAdded(portfolio.getAccountWithKey(accountKey).get(), 1);
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
		String accountKey = "foo";
		Portfolio portfolio = portfolioThatWillUpdateTransactions(accountKey);
		DiscoveredTransaction transaction = discoveredTransactionWithAccountKey(accountKey);
		synchronizer.synchronize(portfolio, new ArrayList<>(), List.of(transaction), logEntry);
		assertTransactionsAdded(portfolio.getAccountWithKey(accountKey).get(), 1);
		assertTransactionsAddedNotIncremented();
		assertTransactionsUpdatedIncremented();
	}
	
	private static Portfolio portfolioThatWillUpdateTransactions(String accountKey) {
		return portfolioThatWillOnAddTransaction(accountKey, Delta.UPDATED);
	}
	
	private void assertTransactionsUpdatedIncremented() {
		verify(logEntry, times(1)).incrementTransactionsUpdated();
	}
	
	@Test
	public void synchronizeWithDiscoveredTransactionsThatHasNoAssociatedAccountEnsureNoTransactionsAddedOrUpdated() {
		String accountKey = "foo";
		Portfolio portfolio = portfolioWithNoAccountFor(accountKey);
		DiscoveredTransaction transaction = discoveredTransactionWithAccountKey(accountKey);
		synchronizer.synchronize(portfolio, new ArrayList<>(), List.of(transaction), logEntry);
		assertTransactionsAddedNotIncremented();
		assertTransactionsUpdatedNotIncremented();
	}
	
	private static Portfolio portfolioWithNoAccountFor(String accountKey) {
		Portfolio portfolio = Mockito.mock(Portfolio.class);
		doReturn(Optional.empty()).when(portfolio).getAccountWithKey(eq(accountKey));
		return portfolio;
	}
}
