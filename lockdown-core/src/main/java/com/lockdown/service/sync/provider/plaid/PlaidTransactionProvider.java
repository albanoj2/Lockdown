package com.lockdown.service.sync.provider.plaid;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.lockdown.domain.Credentials;
import com.lockdown.service.sync.provider.DiscoveredTransaction;
import com.lockdown.service.sync.provider.ProviderException;
import com.lockdown.service.sync.provider.TransactionProvider;
import com.plaid.client.response.TransactionsGetResponse.Transaction;

public class PlaidTransactionProvider implements TransactionProvider {

	private final PlaidConnection connection;
	private final Credentials credentials;
	
	public PlaidTransactionProvider(PlaidConnection connection, Credentials credentials) {
		this.connection = connection;
		this.credentials = credentials;
	}

	@Override
	public List<DiscoveredTransaction> getTransactions() {
		
		try {
			return connection.getRemoteTransactions(credentials.getAccessToken(), oneYearAgo(), now())
				.stream()
				.map(PlaidTransactionProvider::toDiscoveredTransaction)
				.collect(Collectors.toList());
		} 
		catch (IOException e) {
			throw new ProviderException(e);
		}
	}
	
	private static Date oneYearAgo() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		return cal.getTime();
	}

	private static Date now() {
		return new Date();
	}
	
	private static DiscoveredTransaction toDiscoveredTransaction(Transaction rawTransaction) {
		return new DiscoveredTransaction(
			rawTransaction.getTransactionId(),
			rawTransaction.getAccountId(),
			PlaidConverter.toLocalDate(rawTransaction.getDate()),
			PlaidConverter.toMoney(rawTransaction.getAmount()),
			rawTransaction.getName(),
			rawTransaction.getOriginalDescription(),
			rawTransaction.getPending()
		);
	}
}
