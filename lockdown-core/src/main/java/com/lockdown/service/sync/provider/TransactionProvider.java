package com.lockdown.service.sync.provider;

import java.util.List;

import com.lockdown.domain.Account;
import com.lockdown.domain.Transaction;

public interface TransactionProvider {
	public List<Transaction> getTransactions(Account account);
}
