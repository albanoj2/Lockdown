package com.lockdown.service.sync;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Account;
import com.lockdown.domain.Delta;
import com.lockdown.domain.Portfolio;
import com.lockdown.domain.SynchronizationLogEntry;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.DiscoveredTransaction;

@Component
public class AppendingPortfolioSynchronizer extends PortfolioSynchronizer {
	
	private static final Logger logger = LoggerFactory.getLogger(AppendingPortfolioSynchronizer.class);

	@Override
	protected void synchronizeAccounts(Portfolio portfolio, List<DiscoveredAccount> discoveredAccounts, SynchronizationLogEntry logEntry) {
		
		for (DiscoveredAccount discoveredAccount: discoveredAccounts) {
			Delta delta = portfolio.addAccountIfNotExists(discoveredAccount.toAccount());
			
			if (delta == Delta.ADDED) {
				logEntry.incrementAccountsAdded();
			}
		}
	}

	@Override
	protected void synchronizeTransactions(Portfolio portfolio, List<DiscoveredTransaction> discoveredTransactions, SynchronizationLogEntry logEntry) {
		
		for (DiscoveredTransaction transaction: discoveredTransactions) {
			Optional<Account> associatedAccount = portfolio.getAccountWithKey(transaction.getAccountKey());
			
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
	}

	private static String getNoAssociatedAccountMessageFor(DiscoveredTransaction discoveredTransaction) {
		return "Failed to synchronize transaction: Could not find associated account with key " + 
				discoveredTransaction.getAccountKey() + " for transaction: " + discoveredTransaction;
	}
}
