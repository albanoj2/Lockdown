package com.lockdown.service.sync.provider.plaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Credentials;
import com.lockdown.service.sync.provider.AccountProvider;
import com.lockdown.service.sync.provider.ProviderFactory;
import com.lockdown.service.sync.provider.TransactionProvider;

@Component
public class PlaidProviderFactory implements ProviderFactory {
	
	@Autowired
	private PlaidConnection connection;

	@Override
	public AccountProvider createAccountProvider(Credentials credentials) {
		return new PlaidAccountProvider(connection, credentials);
	}

	@Override
	public TransactionProvider createTransactionProvider(Credentials credentials) {
		return new PlaidTransactionProvider(connection, credentials);
	}

}
