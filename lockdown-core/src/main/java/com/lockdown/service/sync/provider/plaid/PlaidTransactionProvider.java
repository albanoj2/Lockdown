package com.lockdown.service.sync.provider.plaid;

import java.util.List;

import com.lockdown.domain.Account;
import com.lockdown.domain.Credentials;
import com.lockdown.domain.Transaction;
import com.lockdown.service.sync.provider.TransactionProvider;

public class PlaidTransactionProvider extends PlaidServiceConsumer implements TransactionProvider {

	private final Credentials credentials;
	
	public PlaidTransactionProvider(PlaidConnection connection, Credentials credentials) {
		super(connection);
		this.credentials = credentials;
	}

	@Override
	public List<Transaction> getTransactions(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

}
