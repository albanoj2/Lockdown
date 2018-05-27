package com.lockdown.rest.controller.model;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

import com.lockdown.domain.Account;
import com.lockdown.rest.LinkRel;
import com.lockdown.rest.controller.AccountsController;
import com.lockdown.rest.controller.PortfoliosController;
import com.lockdown.rest.controller.TransactionsController;

public class AccountModel {

	private final Account account;
	private final String portfolioId;
	
	public AccountModel(Account account, String portfolioId) {
		this.account = account;
		this.portfolioId = portfolioId;
	}

	public Account getAccount() {
		return account;
	}
	
	public String getAccountId() {
		return account.getId();
	}

	public String getPortfolioId() {
		return portfolioId;
	}

	public Link getSelfLink() {
		return linkTo(methodOn(AccountsController.class).getAccount(getPortfolioId(), getAccountId())).withSelfRel();
	}

	public Link getTransactionsLink() {
		return linkTo(methodOn(TransactionsController.class).getTransactions(getPortfolioId(), getAccountId())).withRel(LinkRel.TRANSACTIONS.getRel());
	}

	public Link getPortfolioLink() {
		return linkTo(methodOn(PortfoliosController.class).getPortfolio(getPortfolioId())).withRel(LinkRel.PORTFOLIO.getRel());
	}
}
