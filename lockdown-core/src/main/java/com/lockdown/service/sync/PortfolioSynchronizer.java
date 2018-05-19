package com.lockdown.service.sync;

import java.util.List;

import com.lockdown.domain.Portfolio;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.DiscoveredTransaction;

public abstract class PortfolioSynchronizer {
	
	public void synchronize(Portfolio portfolio, List<DiscoveredAccount> discoveredAccounts, List<DiscoveredTransaction> discoveredTransactions) {
		synchronizeAccounts(portfolio, discoveredAccounts);
		synchronizeTransactions(portfolio, discoveredTransactions);
	}
	
	protected abstract void synchronizeAccounts(Portfolio portfolio, List<DiscoveredAccount> discoveredAccounts);
	protected abstract void synchronizeTransactions(Portfolio portfolio, List<DiscoveredTransaction> discoveredTransactions);
}
