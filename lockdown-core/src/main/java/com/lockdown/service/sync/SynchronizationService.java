package com.lockdown.service.sync;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Credentials;
import com.lockdown.domain.Portfolio;
import com.lockdown.domain.SynchronizationLogEntry;
import com.lockdown.persist.store.PortfolioDataStore;
import com.lockdown.persist.store.SynchronizationLogEntryDataStore;
import com.lockdown.service.sync.provider.AccountProvider;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.DiscoveredTransaction;
import com.lockdown.service.sync.provider.ProviderFactory;
import com.lockdown.service.sync.provider.TransactionProvider;

@Service
public class SynchronizationService {
	
	private static final Logger logger = LoggerFactory.getLogger(SynchronizationService.class);

	@Autowired
	private PortfolioDataStore portfolioDataStore;
	
	@Autowired
	private ProviderFactory providerFactory;
	
	@Autowired
	private PortfolioSynchronizer synchronizer;
	
	@Autowired
	private SynchronizationLogEntryDataStore synchronizationLogEntryDataStore;
	
	@PostConstruct
	public void onCreate() {
		synchronize();
	}
	
	public void synchronize() {
		
		List<Portfolio> portfolios = portfolioDataStore.findAll();
				
		SynchronizationLogEntry logEntry = new SynchronizationLogEntry();
		logEntry.start();
		
		for (Portfolio portfolio: portfolios) {
			beforeSynchronization(portfolio);
			synchronizePortfolio(portfolio, logEntry);
			portfolioDataStore.saveAndCascade(portfolio);
		}
		
		logEntry.stop();
		afterSynchronization(logEntry);
	}

	private void beforeSynchronization(Portfolio portfolio) {
		logger.info("Starting synchronization for portfolio [number of credentials: " + portfolio.getCredentials().size() + "]");
	}
	
	private void synchronizePortfolio(Portfolio portfolio, SynchronizationLogEntry logEntry) {
		
		for (Credentials credentials: portfolio.getCredentials()) {
			
			List<DiscoveredAccount> discoveredAccounts = discoverAccounts(credentials);
			List<DiscoveredTransaction> discoveredTransactions = discoverTransactions(credentials);
			
			logEntry.incrementAccountsDiscoveredBy(discoveredAccounts.size());
			logEntry.incrementTransactionsDiscoveredBy(discoveredTransactions.size());
			
			synchronizer.synchronize(portfolio, discoveredAccounts, discoveredTransactions, logEntry);
		}
	}
	
	private List<DiscoveredAccount> discoverAccounts(Credentials credentials) {
		AccountProvider accountProvider = providerFactory.createAccountProvider(credentials);
		return accountProvider.getAccounts();
	}
	
	private List<DiscoveredTransaction> discoverTransactions(Credentials credentials) {
		TransactionProvider transactionProvider = providerFactory.createTransactionProvider(credentials);
		return transactionProvider.getTransactions();
	}

	private void afterSynchronization(SynchronizationLogEntry logEntry) {
		logger.info("Completed synchronization: " + logEntry);
		synchronizationLogEntryDataStore.saveAndCascade(logEntry);
	}
}
