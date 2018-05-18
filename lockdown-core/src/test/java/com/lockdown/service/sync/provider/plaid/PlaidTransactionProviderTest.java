package com.lockdown.service.sync.provider.plaid;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lockdown.domain.Credentials;
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
	
//	@Test
//	public void givenOneRemoteTransactionsWhenGetTransactionsEnsureNoDiscoveredTransactions() throws Exception {
//		configureRemoteTransactions(List.of(mock(Transaction.class)));
//		assertEquals(1, provider.getTransactions().size());
//	}
}
