package com.lockdown.service.sync.provider;

import java.util.ArrayList;

import com.lockdown.domain.Account;
import com.lockdown.domain.Account.Subtype;
import com.lockdown.domain.Account.Type;
import com.lockdown.domain.Institution;

public class DiscoveredAccount {

	private final String key;
	private final String name;
	private final Institution institution;
	private final Type type;
	private final Subtype subtype;
	
	public DiscoveredAccount(String key, String name, Institution institution, Type type, Subtype subtype) {
		this.key = key;
		this.name = name;
		this.institution = institution;
		this.type = type;
		this.subtype = subtype;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public Institution getInstitution() {
		return institution;
	}

	public Type getType() {
		return type;
	}

	public Subtype getSubtype() {
		return subtype;
	}
	
	public Account toAccount() {
		return new Account(null, key, name, institution, type, subtype, new ArrayList<>());	
	}
}
