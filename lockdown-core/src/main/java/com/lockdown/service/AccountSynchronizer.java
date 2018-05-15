package com.lockdown.service;

import com.lockdown.domain.account.Account;
import com.lockdown.service.context.AccountSynchronizerContext;

public interface AccountSynchronizer {
	public void synchronize(Account account, AccountSynchronizerContext context);
}
