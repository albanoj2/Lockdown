package com.lockdown.rest.controller.model;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.lockdown.domain.Transaction;
import com.lockdown.rest.LinkRel;
import com.lockdown.rest.controller.AccountsController;
import com.lockdown.rest.controller.PortfoliosController;
import com.lockdown.rest.controller.TransactionsController;

public class TransactionModel {

	
	private final Transaction transaction;
	private final String portfolioId;
	private final String accountId;
	
	public TransactionModel(Transaction transaction, String portfolioId, String accountId) {
		this.transaction = transaction;
		this.portfolioId = portfolioId;
		this.accountId = accountId;
	}

	public Transaction getTransaction() {
		return transaction;
	}
	
	public String getTransactionId() {
		return transaction.getId();
	}

	public String getPortfolioId() {
		return portfolioId;
	}

	public String getAccountId() {
		return accountId;
	}

	public Link getSelfLink() {
		return getSelf().withSelfRel();
	}
	
	public Link getUpdateCommentLink() {
		return getSelf().slash("comment").withRel("updateComment");
	}
	
	public ControllerLinkBuilder getSelf() {
		return linkTo(methodOn(TransactionsController.class).getTransaction(
			getPortfolioId(), 
			getAccountId(), 
			getTransactionId()
		));
	}
	
	public Link getPortfolioLink() {
		return linkTo(methodOn(PortfoliosController.class).getPortfolio(
			getPortfolioId()
		)).withRel(LinkRel.PORTFOLIO.getRel());
	}

	public Link getAccountLink() {
		return linkTo(methodOn(AccountsController.class).getAccount(getPortfolioId(), getAccountId())).withRel(LinkRel.ACCOUNT.getRel());
	}
}
