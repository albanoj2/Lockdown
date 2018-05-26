package com.lockdown.rest.resource.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Account;
import com.lockdown.rest.controller.AccountsController;
import com.lockdown.rest.resource.AccountResource;

@Component
public class AccountResourceAssembler extends ResourceAssemblerSupport<Account, AccountResource> {
	
	public AccountResourceAssembler() {
		super(AccountsController.class, AccountResource.class);
	}

	public AccountResource toResource(Account account) {
		AccountResource resource = new AccountResource(account);
//		resource.add(linkTo(methodOn(TransactionsController.class).getTranactions(account.getId())).withRel("transactions"));
		return resource;
	}
}
