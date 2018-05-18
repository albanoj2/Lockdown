package com.lockdown.service.sync.provider.plaid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lockdown.domain.Account;
import com.lockdown.domain.Credentials;
import com.lockdown.domain.Transaction;
import com.lockdown.service.sync.provider.ProviderException;
import com.lockdown.service.sync.provider.TransactionProvider;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.TransactionsGetResponse;

import retrofit2.Response;

public class PlaidTransactionProvider extends PlaidServiceConsumer implements TransactionProvider {

	private final Credentials credentials;
	
	public PlaidTransactionProvider(PlaidConnection connection, Credentials credentials) {
		super(connection);
		this.credentials = credentials;
	}

	@Override
	public Map<Account, List<Transaction>> getTransactions(List<Account> accounts) {
		
		try {
			Date startDate = oneYearAgo();
			Date endDate = now();
			Response<TransactionsGetResponse> response = getService()
				.transactionsGet(new TransactionsGetRequest(credentials.getAccessToken(), startDate, endDate))
				.execute();
			
			List<TransactionsGetResponse.Transaction> rawTransactions = response.body().getTransactions();
			
			Map<Account, List<Transaction>> accountsToTransactions = new HashMap<>();
			
			for (Account account: accounts) {
				accountsToTransactions.put(account, new ArrayList<>());
			}
			
			for (TransactionsGetResponse.Transaction rawTransaction: rawTransactions) {
				accountsToTransactions
					.get(keyBasedOn(accounts, rawTransaction))
					.add(PlaidConverter.toTransaction(rawTransaction));
			}
			
			return accountsToTransactions;
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
	
	private static Account keyBasedOn(List<Account> accounts, TransactionsGetResponse.Transaction transaction) {
		return accounts.stream()
			.filter(a -> a.getKey().equals(transaction.getAccountId()))
			.findFirst()
			.get();
	}
}
