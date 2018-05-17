package com.lockdown.service.sync.provider;

import com.lockdown.domain.Credentials;

public interface ProviderFactory {
	public AccountProvider createAccountProvider(Credentials credentials);
	public TransactionProvider createTransactionProvider(Credentials credentials);
}
