package com.lockdown.service.sync;

import java.util.List;
import java.util.Objects;

import com.lockdown.domain.Portfolio;
import com.lockdown.domain.SynchronizationLogEntry;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.DiscoveredTransaction;

public abstract class PortfolioSynchronizer {

	public void synchronize(Portfolio portfolio, List<DiscoveredAccount> discoveredAccounts,
			List<DiscoveredTransaction> discoveredTransactions, SynchronizationLogEntry logEntry) {

		Objects.requireNonNull(portfolio);
		Objects.requireNonNull(discoveredAccounts);
		Objects.requireNonNull(discoveredTransactions);
		Objects.requireNonNull(logEntry);

		synchronizeAccounts(portfolio, discoveredAccounts, logEntry);
		synchronizeTransactions(portfolio, discoveredTransactions, logEntry);
	}

	protected abstract void synchronizeAccounts(Portfolio portfolio, List<DiscoveredAccount> discoveredAccounts,
			SynchronizationLogEntry logEntry);

	protected abstract void synchronizeTransactions(Portfolio portfolio,
			List<DiscoveredTransaction> discoveredTransactions, SynchronizationLogEntry logEntry);
}
