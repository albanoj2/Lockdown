package com.lockdown.service.sync;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Account;
import com.lockdown.domain.Delta;
import com.lockdown.domain.SynchronizationLogEntry;
import com.lockdown.persist.store.AccountDataStore;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.DiscoveredTransaction;

@Component
public class AppendingPortfolioSynchronizer extends PortfolioSynchronizer {
	
	private static final Logger logger = LoggerFactory.getLogger(AppendingPortfolioSynchronizer.class);
	
	private final AccountDataStore accountDataStore;

	@Autowired
	public AppendingPortfolioSynchronizer(AccountDataStore accountDataStore) {
		this.accountDataStore = accountDataStore;
	}

	@Override
	protected void synchronizeAccounts(List<DiscoveredAccount> discoveredAccounts, SynchronizationLogEntry logEntry) {
		
		for (DiscoveredAccount discoveredAccount: discoveredAccounts) {
			addAccountIfNotExists(discoveredAccount, logEntry);
		}
	}
	
	private void addAccountIfNotExists(DiscoveredAccount discoveredAccount, SynchronizationLogEntry logEntry) {
		boolean alreadyExists = accountDataStore.existsByKey(discoveredAccount.getKey());
		
		if (!alreadyExists) {
			accountDataStore.saveAndCascade(discoveredAccount.toAccount());
			logEntry.incrementAccountsAdded();
		}
	}

	@Override
	protected void synchronizeTransactions(List<DiscoveredTransaction> discoveredTransactions, SynchronizationLogEntry logEntry) {
		
		for (DiscoveredTransaction transaction: discoveredTransactions) {
			Optional<Account> associatedAccount = accountDataStore.findByKey(transaction.getAccountKey());
			
			associatedAccount.ifPresentOrElse(
				account -> addTransaction(transaction, account, logEntry),
				() -> logger.warn(getNoAssociatedAccountMessageFor(transaction))
			);
		}
	}

	private void addTransaction(DiscoveredTransaction transaction, Account account, SynchronizationLogEntry logEntry) {
		
		Delta delta = account.addTransactionOrUpdateIfExists(transaction.getKey(), transaction.toTransactionBody());
		
		if (delta == Delta.ADDED) {
			logEntry.incrementTransactionsAdded();
		}
		else if (delta == Delta.UPDATED) {
			logEntry.incrementTransactionsUpdated();
		}
		
		accountDataStore.saveAndCascade(account);
	}

	private static String getNoAssociatedAccountMessageFor(DiscoveredTransaction discoveredTransaction) {
		return "Failed to synchronize transaction: Could not find associated account with key " + 
				discoveredTransaction.getAccountKey() + " for transaction: " + discoveredTransaction;
	}
}
