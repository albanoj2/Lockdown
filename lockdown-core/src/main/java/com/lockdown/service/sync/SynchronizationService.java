package com.lockdown.service.sync;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Credentials;
import com.lockdown.domain.SynchronizationLogEntry;
import com.lockdown.persist.store.CredentialsDataStore;
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
	private CredentialsDataStore credentialsDataStore;
	
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
	
	private void synchronize() {
		
		List<Credentials> credentials = credentialsDataStore.findAll();
		logger.info("Starting synchronization for portfolio [number of credentials: " + credentials.size() + "]");
		
		SynchronizationLogEntry logEntry = new SynchronizationLogEntry();
		logEntry.start();
		
		for (Credentials credential: credentials) {
			
			List<DiscoveredAccount> discoveredAccounts = discoverAccounts(credential);
			List<DiscoveredTransaction> discoveredTransactions = discoverTransactions(credential);
			
			logEntry.incrementAccountsDiscoveredBy(discoveredAccounts.size());
			logEntry.incrementTransactionsDiscoveredBy(discoveredTransactions.size());
			
			synchronizer.synchronize(discoveredAccounts, discoveredTransactions, logEntry);
		}
		
		logEntry.stop();
		afterSynchronization(logEntry);
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
