package com.lockdown.rest.resource.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Credentials;
import com.lockdown.domain.Portfolio;
import com.lockdown.rest.resource.PortfolioResource;

@Component
public class PortfolioResourceAssembler {

	@Autowired 
	private EntityLinks entityLinks;
	
	public PortfolioResource toResource(Portfolio portfolio) {
		PortfolioResource resource = new PortfolioResource(portfolio);
		resource.add(entityLinks.linkToCollectionResource(Credentials.class));
		return resource;
	}
}
