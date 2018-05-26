package com.lockdown.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.domain.Account;
import com.lockdown.domain.Portfolio;
import com.lockdown.persist.store.PortfolioDataStore;
import com.lockdown.rest.resource.AccountResource;
import com.lockdown.rest.resource.assembler.AccountResourceAssembler;

@RestController
@ExposesResourceFor(Account.class)
@RequestMapping("/portfolio/{portfolioId}/account")
public class AccountsController {
	
	@Autowired
	private PortfolioDataStore dataStore;
	
	@Autowired
	private AccountResourceAssembler assembler;

	@GetMapping
	public ResponseEntity<List<AccountResource>> getAccounts(@PathVariable String portfolioId) {
		
		Optional<Portfolio> portfolio = dataStore.findById(portfolioId);
		
		if (portfolio.isPresent()) {
			List<Account> account = portfolio.get().getAccounts();
			return new ResponseEntity<>(assembler.toResources(account), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
