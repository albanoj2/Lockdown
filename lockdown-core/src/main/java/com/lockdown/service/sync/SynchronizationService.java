package com.lockdown.service.sync;

import java.util.List;

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
	
	@Autowired
	private AccountsSynchronizer accountsSynchronizer;
	
	@PostConstruct
	public void onCreate() {
		synchronize();
	}
	
	public void synchronize() {
		
		for (Portfolio portfolio: portfolioDataStore.findAll()) {
			synchronizePortfolio(portfolio);
//			portfolioDataStore.saveAndCascade(portfolio);
		}
	}
	
	private void synchronizePortfolio(Portfolio portfolio) {
		List<Credentials> portfolioCredentials = portfolio.getCredentials();
		
		for (Credentials credentials: portfolioCredentials) {
			AccountProvider accountProvider = providerFactory.createAccountProvider(credentials);
			List<Account> foundAccounts = accountProvider.getAccounts();
			System.out.println(foundAccounts);
//			accountsSynchronizer.synchronizeWith(portfolio, foundAccounts);
//			
//			for (Account account: portfolio.getAccounts()) {
//				synchronizeAccount(account, portfolio, credentials);
//			}
		}
	}
	
	private void synchronizeAccount(Account account, Portfolio portfolio, Credentials credentials) {
		TransactionProvider transactionProvider = providerFactory.createTransactionProvider(credentials);
		List<Transaction> foundTransactions = transactionProvider.getTransactions(account);
		synchronizerFor(account).synchronizeWith(foundTransactions);
	}
	
	private static TransactionsSynchronizer synchronizerFor(Account account) {
		return new TransactionsSynchronizer(account);
	}
}
