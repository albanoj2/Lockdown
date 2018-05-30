package com.lockdown.persist.dto;

import java.util.Collections;
import java.util.List;

import com.lockdown.domain.Account;

public class AccountDto extends Dto {
	
	private final String key;
	private final String name;
	private final String institution;
	private final String type;
	private final String subtype;
	private final List<String> transactionIds;

	public AccountDto(Account account) {
		super(account.getId());
		this.key = account.getKey();
		this.name = account.getName();
		this.institution = account.getInstitution().name();
		this.type = account.getType().name();
		this.subtype = account.getSubtype().name();
		this.transactionIds = toIdList(account.getTransactions());
	}
	
	public AccountDto() {
		this.key = null;
		this.name = null;
		this.institution = null;
		this.type = null;
		this.subtype = null;
		this.transactionIds = Collections.emptyList();
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getInstitution() {
		return institution;
	}

	public String getType() {
		return type;
	}

	public String getSubtype() {
		return subtype;
	}

	public List<String> getTransactionIds() {
		return transactionIds;
	}
}
