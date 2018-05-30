package com.lockdown.service.sync.provider.plaid;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.plaid.client.PlaidApiService;
import com.plaid.client.PlaidClient;
import com.plaid.client.request.AccountsGetRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.AccountsGetResponse;
import com.plaid.client.response.TransactionsGetResponse.Transaction;

@Component
public class DefaultPlaidConnection implements PlaidConnection {

	@Value("${plaid.clientId}")
	private String clientId;
	
	@Value("${plaid.publicKey}")
	private String publicKey;
	
	@Value("${plaid.secret}")
	private String secret;
	
	private PlaidClient getClient() {
		return PlaidClient.newBuilder()
			.clientIdAndSecret(clientId, secret)
			.publicKey(publicKey)
			.developmentBaseUrl()
			.build();
	}
	
	private PlaidApiService getService() {
		return getClient().service();
	}

	@Override
	public AccountsGetResponse getRemoteAccounts(String accessToken) throws IOException {
		return getService()
			.accountsGet(new AccountsGetRequest(accessToken))
			.execute()
			.body();
	}
	
	public List<Transaction> getRemoteTransactions(String accessToken, Date start, Date end) throws IOException {
		return getService()
			.transactionsGet(new TransactionsGetRequest(accessToken, start, end))
			.execute()
			.body()
			.getTransactions();
	}
}
