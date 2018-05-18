package com.lockdown.service.sync.provider;

import java.util.List;

@FunctionalInterface
public interface TransactionProvider {
	public List<DiscoveredTransaction> getTransactions();
}
