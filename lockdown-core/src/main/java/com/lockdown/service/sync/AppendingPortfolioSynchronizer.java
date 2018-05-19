package com.lockdown.service.sync;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Account;
import com.lockdown.domain.Delta;
import com.lockdown.domain.Portfolio;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.DiscoveredTransaction;

@Component
public class AppendingPortfolioSynchronizer extends PortfolioSynchronizer {
	
	private static final Logger logger = LoggerFactory.getLogger(AppendingPortfolioSynchronizer.class);

	@Override
	protected void synchronizeAccounts(Portfolio portfolio, List<DiscoveredAccount> discoveredAccounts) {
		
		SynchronizationStatatics statistics = new SynchronizationStatatics();
		
		for (DiscoveredAccount discoveredAccount: discoveredAccounts) {
			boolean accountWasAdded = portfolio.addAccountIfNotExists(discoveredAccount.toAccount());
			
			if (accountWasAdded) {
				statistics.incrementNumberAdded();
			}
		}
		
		logger.info("Completed synchronization of accounts [found: " + discoveredAccounts.size() + ", added: " + statistics.getNumberAdded() + "]");
	}

	@Override
	protected void synchronizeTransactions(Portfolio portfolio, List<DiscoveredTransaction> discoveredTransactions) {
		
		SynchronizationStatatics statistics = new SynchronizationStatatics();
		
		for (DiscoveredTransaction transaction: discoveredTransactions) {
			Optional<Account> associatedAccount = portfolio.getAccountWithKey(transaction.getAccountKey());
			
			associatedAccount.ifPresentOrElse(
				account -> addTransaction(transaction, account, statistics),
				() -> logger.warn(getNoAssociatedAccountMessageFor(transaction))
			);
		}
		
		logger.info("Completed synchronization of transactions [found: " + discoveredTransactions.size() + 
				", added: " + statistics.getNumberAdded() + ", updated: " + statistics.getNumberUpdated() + "]");
	}

	private void addTransaction(DiscoveredTransaction transaction, Account account, SynchronizationStatatics statistics) {
		
		Delta delta = account.addTransactionOrUpdateIfExists(transaction.getKey(), transaction.toTransactionBody());
		logger.debug("Added discovered transaction " + transaction + " to account " + account);
		
		if (delta == Delta.ADDED) {
			statistics.incrementNumberAdded();
		}
		else if (delta == Delta.UPDATED) {
			statistics.incrementNumberUpdated();
		}
	}

	private static String getNoAssociatedAccountMessageFor(DiscoveredTransaction discoveredTransaction) {
		return "Failed to synchronize transaction: Could not find associated account with key " + 
				discoveredTransaction.getAccountKey() + " for transaction: " + discoveredTransaction;
	}
	
	private static class SynchronizationStatatics {
		
		private int numberAdded;
		private int numberUpdated;
		
		public void incrementNumberAdded() {
			numberAdded++;
		}
		
		public int getNumberAdded() {
			return numberAdded;
		}
		
		public void incrementNumberUpdated() {
			numberUpdated++;
		}
		
		public int getNumberUpdated() {
			return numberUpdated;
		}
	}

}
