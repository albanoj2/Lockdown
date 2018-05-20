package com.lockdown.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lockdown.domain.Portfolio;

public class PortfolioResource extends ResourceSupport {

	private final String portfolioId;
	
	public PortfolioResource(Portfolio portfolio) {
		this.portfolioId = portfolio.getId();
	}

	@JsonProperty("id")
	public String getPortfolioId() {
		return portfolioId;
	}
}
