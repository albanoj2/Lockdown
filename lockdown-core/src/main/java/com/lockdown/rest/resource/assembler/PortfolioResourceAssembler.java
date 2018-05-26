package com.lockdown.rest.resource.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Portfolio;
import com.lockdown.rest.controller.AccountsController;
import com.lockdown.rest.controller.CredentialsController;
import com.lockdown.rest.controller.PortfoliosController;
import com.lockdown.rest.resource.PortfolioResource;

@Component
public class PortfolioResourceAssembler extends ResourceAssemblerSupport<Portfolio, PortfolioResource> {
	
	public PortfolioResourceAssembler() {
		super(PortfoliosController.class, PortfolioResource.class);
	}

	public PortfolioResource toResource(Portfolio portfolio) {
		PortfolioResource resource = new PortfolioResource(portfolio);
		resource.add(linkTo(methodOn(CredentialsController.class).getCredentials(portfolio.getId())).withRel("credentials"));
		resource.add(linkTo(methodOn(AccountsController.class).getAccounts(portfolio.getId())).withRel("accounts"));
		return resource;
	}
}
