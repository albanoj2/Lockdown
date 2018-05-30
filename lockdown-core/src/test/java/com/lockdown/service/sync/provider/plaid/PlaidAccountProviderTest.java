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
import org.mockito.Mockito;

import com.lockdown.domain.Credentials;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.ProviderException;
import com.plaid.client.response.Account;
import com.plaid.client.response.AccountsGetResponse;
import com.plaid.client.response.ItemStatus;

public class PlaidAccountProviderTest {

	private PlaidConnection connection;
	private Credentials credentials;
	private PlaidAccountProvider provider;
	
	@Before
	public void setUp() {
		connection = mock(PlaidConnection.class);
		credentials = mock(Credentials.class);
		provider = new PlaidAccountProvider(connection, credentials);
	}
	
	@Test(expected = ProviderException.class)
	public void givenIoExceptionWhenGetRemoteAccountsEnsureProviderExceptionThrown() throws Exception {
		ensureExceptionThrownWhenGettingRemoteAccounts();
		provider.getAccounts();
	}
	
	private void ensureExceptionThrownWhenGettingRemoteAccounts() throws Exception {
		doThrow(new IOException()).when(connection).getRemoteAccounts(any());
	}
	
	@Test
	public void givenNoRemoteAccountsWhenGetAccountsEnsureNoDiscoveredAccounts() throws Exception {
		configureRemoteAccounts(new ArrayList<>());
		assertTrue(provider.getAccounts().isEmpty());
	}

	private void configureRemoteAccounts(List<Account> remoteAccounts) throws IOException {
		AccountsGetResponse response = Mockito.mock(AccountsGetResponse.class);
		ItemStatus itemStatus = Mockito.mock(ItemStatus.class);
		
		doReturn("foo").when(itemStatus).getInstitutionId();
		doReturn(itemStatus).when(response).getItem();
		doReturn(remoteAccounts).when(response).getAccounts();
		doReturn(response).when(connection).getRemoteAccounts(any());
	}
	
	@Test
	public void givenOneRemoteAccountsWhenGetAccountsEnsureOneDiscoveredAccounts() throws Exception {
		Account account = mockAccount("foo", "foo", "foo", "subFoo");
		configureRemoteAccounts(List.of(account));
		
		assertNumberOfAccountsDiscovered(1);
		assertAccountDataAtIndexEquals(0, account);
	}

	private void assertNumberOfAccountsDiscovered(int number) {
		assertEquals(number, provider.getAccounts().size());
	}

	private void assertAccountDataAtIndexEquals(int index, Account account) {
		assertEqualAccountData(provider.getAccounts().get(index), account);
	}
	
	private static Account mockAccount(String accountId, String name, String type, String subtype) {
		Account account = mock(Account.class);
		doReturn(accountId).when(account).getAccountId();
		doReturn(name).when(account).getName();
		doReturn(type).when(account).getType();
		doReturn(subtype).when(account).getSubtype();
		return account;
	}
	
	private static void assertEqualAccountData(DiscoveredAccount discovered, Account original) {
		assertEquals(original.getAccountId(), discovered.getKey());
		assertEquals(original.getName(), discovered.getName());
		assertEquals(PlaidTypeMapper.toType(original.getType()), discovered.getType());
		assertEquals(PlaidSubtypeMapper.toSubtype(original.getSubtype()), discovered.getSubtype());
	}
	
	@Test
	public void givenTwoRemoteAccountsWhenGetAccountsEnsureTwoDiscoveredAccounts() throws Exception {
		Account account1 = mockAccount("foo", "foo", "foo", "subFoo");
		Account account2 = mockAccount("bar", "bar", "bar", "subBar");
		configureRemoteAccounts(List.of(account1, account2));
		
		assertNumberOfAccountsDiscovered(2);
		assertAccountDataAtIndexEquals(0, account1);
		assertAccountDataAtIndexEquals(1, account2);
	}
}
