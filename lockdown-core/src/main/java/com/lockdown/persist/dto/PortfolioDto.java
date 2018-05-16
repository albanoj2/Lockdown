package com.lockdown.persist.dto;

import java.util.Collections;
import java.util.List;

import com.lockdown.domain.Portfolio;

public class PortfolioDto extends Dto {
	
	private final String budgetId;
	private final List<String> accountIds;
	private final List<String> credentialIds;

	public PortfolioDto(Portfolio portfolio) {
		super(portfolio.getId());
		this.budgetId = portfolio.getBudget().getId();
		this.accountIds = toIdList(portfolio.getAccounts());
		this.credentialIds = toIdList(portfolio.getCredentials());
	}
	
	public PortfolioDto() {
		this.budgetId = null;
		this.accountIds = Collections.emptyList();
		this.credentialIds = Collections.emptyList();
	}

	public String getBudgetId() {
		return budgetId;
	}

	public List<String> getAccountsIds() {
		return accountIds;
	}

	public List<String> getCredentialsIds() {
		return credentialIds;
	}
}
