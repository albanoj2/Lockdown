package com.lockdown.service.sync.provider.plaid;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.lockdown.domain.Account;
import com.lockdown.domain.Credentials;
import com.lockdown.service.sync.provider.AccountProvider;
import com.lockdown.service.sync.provider.ProviderException;
import com.plaid.client.request.AccountsGetRequest;
import com.plaid.client.response.AccountsGetResponse;

import retrofit2.Response;

public class PlaidAccountProvider extends PlaidServiceConsumer implements AccountProvider {
	
	private final Credentials credentials;
	
	public PlaidAccountProvider(PlaidConnection connection, Credentials credentials) {
		super(connection);
		this.credentials = credentials;
	}

	@Override
	public List<Account> getAccounts() {
		
		try {
			Response<AccountsGetResponse> response = getService()
				.accountsGet(new AccountsGetRequest(credentials.getAccessToken()))
				.execute();
			
			return response.body().getAccounts().stream()
				.map(PlaidConverter::toAccount)
				.collect(Collectors.toList());
		} 
		catch (IOException e) {
			throw new ProviderException(e);
		}
	}
}
