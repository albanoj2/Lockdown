package com.lockdown.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.lockdown.rest.controller.model.AccountModel;
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
			List<Account> accounts = portfolio.get().getAccounts();
			List<AccountModel> models = accounts.stream()
				.map(account -> new AccountModel(account, portfolioId))
				.collect(Collectors.toList());
			return new ResponseEntity<>(assembler.toResources(models), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{accountId}")
	public ResponseEntity<AccountResource> getAccount(@PathVariable String portfolioId, @PathVariable String accountId) {
		
		Optional<Portfolio> portfolio = dataStore.findById(portfolioId);
		
		if (portfolio.isPresent()) {
			Optional<Account> account = portfolio.get().getAccountWithId(accountId);
			
			if (account.isPresent()) {
				AccountModel model = new AccountModel(account.get(), portfolioId);
				return new ResponseEntity<>(assembler.toResource(model), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
