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
import com.lockdown.domain.Transaction;
import com.lockdown.persist.store.PortfolioDataStore;
import com.lockdown.rest.resource.TransactionResource;
import com.lockdown.rest.resource.assembler.TransactionResourceAssembler;

@RestController
@ExposesResourceFor(Transaction.class)
@RequestMapping("/portfolio/{portfolioId}/account/{accountId}/transaction")
public class TransactionsController {

	@Autowired
	private PortfolioDataStore dataStore;
	
	@Autowired
	private TransactionResourceAssembler assembler;

	@GetMapping
	public ResponseEntity<List<TransactionResource>> getTransactions(@PathVariable String portfolioId, @PathVariable String accountId) {
		
		Optional<Portfolio> portfolio = dataStore.findById(portfolioId);
		
		if (portfolio.isPresent()) {
			Optional<Account> account = portfolio.get().getAccountWithId(accountId);
			
			if (account.isPresent()) {
				return new ResponseEntity<>(assembler.toResources(account.get().getTransactions()), HttpStatus.OK);
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
