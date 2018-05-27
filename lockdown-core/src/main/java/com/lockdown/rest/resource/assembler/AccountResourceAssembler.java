package com.lockdown.rest.resource.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lockdown.rest.controller.AccountsController;
import com.lockdown.rest.controller.model.AccountModel;
import com.lockdown.rest.resource.AccountResource;

@Component
public class AccountResourceAssembler extends ResourceAssemblerSupport<AccountModel, AccountResource> {
	
	public AccountResourceAssembler() {
		super(AccountsController.class, AccountResource.class);
	}

	public AccountResource toResource(AccountModel model) {
		AccountResource resource = new AccountResource(model.getAccount());
		resource.add(model.getTransactionsLink());
		resource.add(model.getPortfolioLink());
		resource.add(model.getSelfLink());
		return resource;
	}
}
