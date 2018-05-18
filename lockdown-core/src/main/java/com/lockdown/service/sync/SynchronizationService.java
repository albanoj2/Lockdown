package com.lockdown.service.sync;

import java.util.List;

import javax.annotation.PostConstruct;

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

	@Autowired
	private PortfolioDataStore portfolioDataStore;
	
	@Autowired
	private ProviderFactory providerFactory;
	
	@PostConstruct
	public void onCreate() {
		synchronize();
	}
	
	public void synchronize() {
		
		for (Portfolio portfolio: portfolioDataStore.findAll()) {
			synchronizePortfolio(portfolio);
		}
	}
	
	private void synchronizePortfolio(Portfolio portfolio) {
		List<Credentials> portfolioCredentials = portfolio.getCredentials();
		
		for (Credentials credentials: portfolioCredentials) {
			AccountProvider accountProvider = providerFactory.createAccountProvider(credentials);
			List<DiscoveredAccount> foundAccounts = accountProvider.getAccounts();
			System.out.println(foundAccounts);
			synchronizeAccounts(portfolio, credentials);
		}
	}
	
	private void synchronizeAccounts(Portfolio portfolio, Credentials credentials) {
		TransactionProvider transactionProvider = providerFactory.createTransactionProvider(credentials);
		List<DiscoveredTransaction> foundTransactions = transactionProvider.getTransactions();
		System.out.println(foundTransactions);
	}
}
