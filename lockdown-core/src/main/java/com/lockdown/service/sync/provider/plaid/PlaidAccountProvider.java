package com.lockdown.service.sync.provider.plaid;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.lockdown.domain.Credentials;
import com.lockdown.service.sync.provider.AccountProvider;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.ProviderException;
import com.plaid.client.response.Account;
import com.plaid.client.response.AccountsGetResponse;

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
			AccountsGetResponse response = connection.getRemoteAccounts(credentials.getAccessToken());
			String institutionId = response.getItem().getInstitutionId();
			
			return response.getAccounts()
				.stream()
				.map(account -> toDiscoveredAccount(account, institutionId))
				.collect(Collectors.toList());
		} 
		catch (IOException e) {
			throw new ProviderException(e);
		}
	}
	
	public static DiscoveredAccount toDiscoveredAccount(Account input, String institutionId) {
		return new DiscoveredAccount(
			input.getAccountId(), 
			input.getName(),
			PlaidInstitutionMapper.toInstitution(institutionId),
			PlaidTypeMapper.toType(input.getType()), 
			PlaidSubtypeMapper.toSubtype(input.getSubtype())
		);
	}
}
