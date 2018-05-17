package com.lockdown.service.sync.provider.plaid;

import java.util.List;

import com.lockdown.domain.Account;
import com.lockdown.domain.Credentials;
import com.lockdown.domain.Transaction;
import com.lockdown.service.sync.provider.TransactionProvider;

public class PlaidTransactionProvider implements TransactionProvider {

	private final Credentials credentials;
	
	public PlaidTransactionProvider(Credentials credentials) {
		this.credentials = credentials;
	}

	@Override
	public List<Transaction> getTransactions(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

}
