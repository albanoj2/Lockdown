package com.lockdown.persist.dto;

import java.util.Collections;
import java.util.List;

import com.lockdown.domain.Account;

public class AccountDto extends Dto {
	
	private final String key;
	private final String name;
	private final String type;
	private final List<String> transactionIds;

	public AccountDto(Account account) {
		super(account.getId());
		this.key = account.getKey();
		this.name = account.getName();
		this.type = account.getType();
		this.transactionIds = toIdList(account.getTransactions());
	}
	
	public AccountDto() {
		this.key = null;
		this.name = null;
		this.type = null;
		this.transactionIds = Collections.emptyList();
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public List<String> getTransactionIds() {
		return transactionIds;
	}
}
