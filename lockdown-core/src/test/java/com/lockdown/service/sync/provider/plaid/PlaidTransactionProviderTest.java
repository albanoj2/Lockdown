package com.lockdown.service.sync.provider.plaid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lockdown.domain.Credentials;
import com.lockdown.service.sync.provider.DiscoveredTransaction;
import com.lockdown.service.sync.provider.ProviderException;
import com.plaid.client.response.TransactionsGetResponse.Transaction;

public class PlaidTransactionProviderTest {

	private PlaidConnection connection;
	private Credentials credentials;
	private PlaidTransactionProvider provider;
	
	@Before
	public void setUp() {
		connection = mock(PlaidConnection.class);
		credentials = mock(Credentials.class);
		provider = new PlaidTransactionProvider(connection, credentials);
	}
	
	@Test(expected = ProviderException.class)
	public void givenIoExceptionWhenGetRemoteTransactionsEnsureProviderExceptionThrown() throws Exception {
		ensureExceptionThrownWhenGettingRemoteTransactions();
		provider.getTransactions();
	}
	
	private void ensureExceptionThrownWhenGettingRemoteTransactions() throws Exception {
		doThrow(new IOException()).when(connection).getRemoteTransactions(any(), any(), any());
	}
	
	@Test
	public void givenNoRemoteTransactionsWhenGetTransactionsEnsureNoDiscoveredTransactions() throws Exception {
		configureRemoteTransactions(new ArrayList<>());
		assertTrue(provider.getTransactions().isEmpty());
	}

	private void configureRemoteTransactions(List<Transaction> remoteTransactions) throws IOException {
		doReturn(remoteTransactions).when(connection).getRemoteTransactions(any(), any(), any());
	}
	
	@Test
	public void givenOneRemoteTransactionsWhenGetTransactionsEnsureOneDiscoveredTransactions() throws Exception {
		Transaction transaction = mockTransaction("foo", "2018-01-30", 1.00, "foo", "foo", false);
		configureRemoteTransactions(List.of(transaction));
		
		assertNumberOfTransactionsDiscovered(1);
		assertTransactionDataAtIndexEquals(0, transaction);
	}

	private void assertNumberOfTransactionsDiscovered(int number) {
		assertEquals(number, provider.getTransactions().size());
	}

	private void assertTransactionDataAtIndexEquals(int index, Transaction transaction) {
		assertEqualTransactionData(provider.getTransactions().get(index), transaction);
	}
	
	private static Transaction mockTransaction(String accountId, String date, double amount, String name, String description, boolean isPending) {
		Transaction transaction = mock(Transaction.class);
		doReturn(accountId).when(transaction).getAccountId();
		doReturn(date).when(transaction).getDate();
		doReturn(amount).when(transaction).getAmount();
		doReturn(name).when(transaction).getName();
		doReturn(description).when(transaction).getOriginalDescription();
		doReturn(isPending).when(transaction).getPending();
		return transaction;
	}
	
	private static void assertEqualTransactionData(DiscoveredTransaction discovered, Transaction original) {
		assertEquals(original.getAccountId(), discovered.getAccountKey());
		assertEquals(PlaidConverter.toLocalDate(original.getDate()), discovered.getDate());
		assertEquals(PlaidConverter.toMoney(original.getAmount()), discovered.getAmount());
		assertEquals(original.getName(), discovered.getName());
		assertEquals(original.getOriginalDescription(), discovered.getDescription());
		assertEquals(original.getPending(), discovered.isPending());
	}
	
	@Test
	public void givenTwoRemoteTransactionsWhenGetTransactionsEnsureTwoDiscoveredTransactions() throws Exception {
		Transaction transaction1 = mockTransaction("foo", "2018-01-30", 1.00, "foo", "foo", false);
		Transaction transaction2 = mockTransaction("bar", "2018-02-15", 2.00, "bar", "bar", true);
		configureRemoteTransactions(List.of(transaction1, transaction2));
		
		assertNumberOfTransactionsDiscovered(2);
		assertTransactionDataAtIndexEquals(0, transaction1);
		assertTransactionDataAtIndexEquals(1, transaction2);
	}
}
