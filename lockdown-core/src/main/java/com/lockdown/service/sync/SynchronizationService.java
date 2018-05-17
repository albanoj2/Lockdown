package com.lockdown.service.sync;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Account;
import com.lockdown.domain.Credentials;
import com.lockdown.domain.Portfolio;
import com.lockdown.domain.Transaction;
import com.lockdown.persist.store.PortfolioDataStore;
import com.lockdown.service.sync.provider.AccountProvider;
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
			List<Account> foundAccounts = accountProvider.getAccounts();
			System.out.println(foundAccounts);
			synchronizeAccounts(foundAccounts, portfolio, credentials);
		}
	}
	
	private void synchronizeAccounts(List<Account> accounts, Portfolio portfolio, Credentials credentials) {
		TransactionProvider transactionProvider = providerFactory.createTransactionProvider(credentials);
		Map<Account, List<Transaction>> foundTransactions = transactionProvider.getTransactions(accounts);
		System.out.println(foundTransactions);
	}
}
