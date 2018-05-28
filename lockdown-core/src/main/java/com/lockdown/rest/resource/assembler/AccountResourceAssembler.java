package com.lockdown.rest.resource.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Account;
import com.lockdown.rest.LinkRel;
import com.lockdown.rest.controller.AccountsController;
import com.lockdown.rest.resource.AccountResource;

@Component
public class AccountResourceAssembler extends ResourceAssemblerSupport<Account, AccountResource> {
	
	public AccountResourceAssembler() {
		super(AccountsController.class, AccountResource.class);
	}

	public AccountResource toResource(Account account) {
		AccountResource resource = new AccountResource(account);
		resource.add(getTransactionsLink(account.getId()));
		resource.add(getSelfLink(account.getId()));
		return resource;
	}
	
	private static Link getSelfLink(String accountId) {
		return linkTo(methodOn(AccountsController.class).getAccount(accountId)).withSelfRel();
	}

	private static Link getTransactionsLink(String accountId) {
		return linkTo(methodOn(AccountsController.class).getTransactions(accountId)).withRel(LinkRel.TRANSACTIONS.getRel());
	}
}
