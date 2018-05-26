package com.lockdown.rest.resource.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Portfolio;
import com.lockdown.rest.controller.AccountsController;
import com.lockdown.rest.controller.TransactionsController;
import com.lockdown.rest.controller.model.AccountModel;
import com.lockdown.rest.resource.AccountResource;

@Component
public class AccountResourceAssembler extends ResourceAssemblerSupport<AccountModel, AccountResource> {
	
	@Autowired
	private EntityLinks entityLinks;
	
	public AccountResourceAssembler() {
		super(AccountsController.class, AccountResource.class);
	}

	public AccountResource toResource(AccountModel model) {
		AccountResource resource = new AccountResource(model.getAccount());
		resource.add(linkTo(methodOn(TransactionsController.class).getTransactions(model.getPortfolioId(), model.getAccountId())).withRel("transactions"));
		resource.add(entityLinks.linkToSingleResource(Portfolio.class, model.getPortfolioId()).withRel("portfolio"));
		resource.add(linkTo(methodOn(AccountsController.class).getAccount(model.getPortfolioId(), model.getAccountId())).withSelfRel());
		return resource;
	}
}
