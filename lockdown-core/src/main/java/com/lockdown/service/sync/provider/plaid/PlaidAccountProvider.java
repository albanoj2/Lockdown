package com.lockdown.service.sync.provider.plaid;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.lockdown.domain.Credentials;
import com.lockdown.service.sync.provider.AccountProvider;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.ProviderException;
import com.plaid.client.response.Account;

public class PlaidAccountProvider implements AccountProvider {
	
	private final PlaidConnection connection;
	private final Credentials credentials;
	
	public PlaidAccountProvider(PlaidConnection connection, Credentials credentials) {
		this.connection = connection;
		this.credentials = credentials;
	}

	@Override
	public List<DiscoveredAccount> getAccounts() {
		
		try {
			return connection
				.getRemoteAccounts(credentials.getAccessToken())
				.stream()
				.map(PlaidAccountProvider::toDiscoveredAccount)
				.collect(Collectors.toList());
		} 
		catch (IOException e) {
			throw new ProviderException(e);
		}
	}
	
	public static DiscoveredAccount toDiscoveredAccount(Account input) {
		return new DiscoveredAccount(
			input.getAccountId(), 
			input.getName(), 
			input.getType(), 
			input.getSubtype()
		);
	}
}
