package com.lockdown.service.sync.provider;

import java.util.List;

public interface AccountProvider {
	public List<DiscoveredAccount> getAccounts();
}
