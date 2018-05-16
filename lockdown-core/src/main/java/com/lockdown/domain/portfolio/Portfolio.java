package com.lockdown.domain.portfolio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.lockdown.domain.account.Account;
import com.lockdown.domain.account.Credentials;
import com.lockdown.domain.budget.Budget;

@Entity
public class Portfolio {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private final Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private final Budget budget;
	
	@OneToMany(cascade = CascadeType.ALL)
	private final List<Account> accounts;
	
	@OneToMany(cascade = CascadeType.ALL)
	private final List<Credentials> credentials;
	
	public Portfolio(Long id, Budget budget, List<Account> accounts, List<Credentials> credentials) {
		this.id = id;
		this.budget = budget;
		this.accounts = accounts;
		this.credentials = credentials;
	}
	
	public Portfolio() {
		this.id = null;
		this.budget = Budget.empty();
		this.accounts = new ArrayList<>();
		this.credentials = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public Budget getBudget() {
		return budget;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public List<Credentials> getCredentials() {
		return credentials;
	}
}
