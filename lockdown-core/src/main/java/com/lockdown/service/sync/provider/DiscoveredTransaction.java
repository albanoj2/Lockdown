package com.lockdown.service.sync.provider;

import java.time.LocalDate;

import com.lockdown.domain.Account;
import com.lockdown.domain.Money;
import com.lockdown.domain.TransactionBody;

public class DiscoveredTransaction {

	private final String accountKey;
	private final String key;
	private final TransactionBody body;
	
	public DiscoveredTransaction(String key, String accountKey, LocalDate date, Money amount, String name, String description, boolean isPending) {
		this.accountKey = accountKey;
		this.key = key;
		this.body = new TransactionBody(date, amount, name, description, isPending);
	}

	public String getAccountKey() {
		return accountKey;
	}

	public String getKey() {
		return key;
	}

	public TransactionBody getBody() {
		return body;
	}
	
	public LocalDate getDate() {
		return body.getDate();
	}
	
	public Money getAmount() {
		return body.getAmount();
	}
	
	public String getName() {
		return body.getName();
	}
	
	public String getDescription() {
		return body.getDescription();
	}
	
	public boolean isPending() {
		return body.isPending();
	}
	
	public boolean isAssociatedWith(Account account) {
		return account.getKey().equals(accountKey);
	}
}
