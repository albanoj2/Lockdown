package com.lockdown.service.sync.provider;

import java.util.List;
import java.util.Map;

import com.lockdown.domain.Account;
import com.lockdown.domain.Transaction;

public interface TransactionProvider {
	public Map<Account, List<Transaction>> getTransactions(List<Account> accounts);
}
