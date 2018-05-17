package com.lockdown.service.sync.provider.plaid;

import com.plaid.client.PlaidApiService;

public abstract class PlaidServiceConsumer {

	private final PlaidConnection connection;
	
	protected PlaidServiceConsumer(PlaidConnection connection) {
		this.connection = connection;
	}
	
	protected PlaidApiService getService() {
		return connection.getClient().service();
	}
}
