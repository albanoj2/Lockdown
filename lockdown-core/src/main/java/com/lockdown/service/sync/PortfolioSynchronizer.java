package com.lockdown.service.sync;

import java.util.List;
import java.util.Objects;

import com.lockdown.domain.SynchronizationLogEntry;
import com.lockdown.service.sync.provider.DiscoveredAccount;
import com.lockdown.service.sync.provider.DiscoveredTransaction;

public abstract class PortfolioSynchronizer {
	
	public void synchronize(List<DiscoveredAccount> discoveredAccounts,
			List<DiscoveredTransaction> discoveredTransactions, SynchronizationLogEntry logEntry) {

		Objects.requireNonNull(discoveredAccounts);
		Objects.requireNonNull(discoveredTransactions);
		Objects.requireNonNull(logEntry);

		synchronizeAccounts(discoveredAccounts, logEntry);
		synchronizeTransactions(discoveredTransactions, logEntry);
	}

	protected abstract void synchronizeAccounts(List<DiscoveredAccount> discoveredAccounts,
			SynchronizationLogEntry logEntry);

	protected abstract void synchronizeTransactions(List<DiscoveredTransaction> discoveredTransactions, SynchronizationLogEntry logEntry);
}
