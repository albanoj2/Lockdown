package com.lockdown.service.sync.provider.plaid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.plaid.client.PlaidClient;

@Component
public class PlaidConnection {

	@Value("${plaid.clientId}")
	private String clientId;
	
	@Value("${plaid.publicKey}")
	private String publicKey;
	
	@Value("${plaid.secret}")
	private String secret;
	
	public PlaidClient getClient() {
		return PlaidClient.newBuilder()
			.clientIdAndSecret(clientId, secret)
			.publicKey(publicKey)
			.developmentBaseUrl()
			.build();
	}
}
