package com.lockdown.service.sync.provider.plaid;

import java.util.List;

import com.lockdown.domain.Account;
import com.lockdown.domain.Credentials;
import com.lockdown.service.sync.provider.AccountProvider;

public class PlaidAccountProvider implements AccountProvider {
	
	private final Credentials credentials;
	
	public PlaidAccountProvider(Credentials credentials) {
		this.credentials = credentials;
	}

	@Override
	public List<Account> getAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

}
