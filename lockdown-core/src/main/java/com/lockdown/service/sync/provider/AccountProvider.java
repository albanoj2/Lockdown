package com.lockdown.service.sync.provider;

import java.util.List;

import com.lockdown.domain.Account;

public interface AccountProvider {
	public List<Account> getAccounts();
}
