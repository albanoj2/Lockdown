package com.lockdown.service.sync;

import java.util.List;
import java.util.Optional;

import com.lockdown.domain.Account;
import com.lockdown.domain.Transaction;

public class TransactionsSynchronizer {
	
	private final Account account;
	
	public TransactionsSynchronizer(Account account) {
		this.account = account;
	}

	public void synchronizeWith(List<Transaction> foundTransactions) {
		
	}
	
	@SuppressWarnings("unused")
	private Optional<Transaction> getExisting(Transaction transaction) {
		return account.getTransactions().stream()
			.filter(existingTransaction -> transaction.getKey().equals(existingTransaction.getKey()))
			.findFirst();
	}
}
