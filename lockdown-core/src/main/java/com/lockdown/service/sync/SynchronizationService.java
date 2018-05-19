package com.lockdown.service.sync;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Credentials;
import com.lockdown.domain.Portfolio;
import com.lockdown.persist.store.PortfolioDataStore;
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
	
	@PostConstruct
	public void onCreate() {
		synchronize();
	}
	
	public void synchronize() {
		
		List<Portfolio> portfolios = portfolioDataStore.findAll();
				
		for (Portfolio portfolio: portfolios) {
			logger.info("Starting synchronization for portfolio [number of credentials: " + portfolio.getCredentials().size() + "]");
			synchronizePortfolio(portfolio);
			portfolioDataStore.saveAndCascade(portfolio);
			logger.info("Completed synchronization");
		}
	}
	
	private void synchronizePortfolio(Portfolio portfolio) {
		
		List<Credentials> portfolioCredentials = portfolio.getCredentials();
		
		for (Credentials credentials: portfolioCredentials) {
			AccountProvider accountProvider = providerFactory.createAccountProvider(credentials);
			TransactionProvider transactionProvider = providerFactory.createTransactionProvider(credentials);
			List<DiscoveredAccount> discoveredAccounts = accountProvider.getAccounts();
			List<DiscoveredTransaction> discoveredTransactions = transactionProvider.getTransactions();
			synchronizer.synchronize(portfolio, discoveredAccounts, discoveredTransactions);
		}
	}
}
