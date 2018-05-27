package com.lockdown.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.domain.Account;
import com.lockdown.domain.Portfolio;
import com.lockdown.domain.Transaction;
import com.lockdown.persist.store.PortfolioDataStore;
import com.lockdown.persist.store.TransactionDataStore;
import com.lockdown.rest.controller.model.TransactionModel;
import com.lockdown.rest.error.ResourceNotFoundException;
import com.lockdown.rest.resource.TransactionResource;
import com.lockdown.rest.resource.assembler.TransactionResourceAssembler;

@RestController
@ExposesResourceFor(Transaction.class)
@RequestMapping("/portfolio/{portfolioId}/account/{accountId}/transaction")
public class TransactionsController {

	@Autowired
	private PortfolioDataStore portfolioDataStore;
	
	@Autowired
	private TransactionDataStore transactionDataStore;
	
	@Autowired
	private TransactionResourceAssembler assembler;

	@GetMapping
	public ResponseEntity<List<TransactionResource>> getTransactions(@PathVariable String portfolioId, @PathVariable String accountId) {
		
		Optional<Portfolio> portfolio = portfolioDataStore.findById(portfolioId);
		
		if (portfolio.isPresent()) {
			Optional<Account> account = portfolio.get().getAccountWithId(accountId);
			
			if (account.isPresent()) {
				List<TransactionModel> models = account.get().getTransactions()
					.stream()
					.map(transaction -> new TransactionModel(transaction, portfolioId, accountId))
					.collect(Collectors.toList());
				
				return new ResponseEntity<>(assembler.toResources(models), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	private Transaction getTransactionFromDataStore(String portfolioId, String accountId, String transactionId) throws ResourceNotFoundException {
		return portfolioDataStore.findById(portfolioId)
			.orElseThrow(ResourceNotFoundException.supplierForResource("portfolio", portfolioId))
			.getAccountWithId(accountId)
			.orElseThrow(ResourceNotFoundException.supplierForResource("account", accountId))
			.getTransactionById(transactionId)
			.orElseThrow(ResourceNotFoundException.supplierForResource("transaction", transactionId));
	}
	
	@GetMapping("/{transactionId}")
	public ResponseEntity<TransactionResource> getTransaction(@PathVariable String portfolioId, @PathVariable String accountId, @PathVariable String transactionId) {
		Transaction transaction = getTransactionFromDataStore(portfolioId, accountId, transactionId);
		TransactionModel model = new TransactionModel(transaction, portfolioId, accountId);
		return new ResponseEntity<>(assembler.toResource(model), HttpStatus.OK);
	}
	
	@PatchMapping("/{transactionId}/comment")
	public ResponseEntity<?> addComment(@PathVariable String portfolioId, @PathVariable String accountId, @PathVariable String transactionId, @RequestBody String comment) {
		Transaction transaction = getTransactionFromDataStore(portfolioId, accountId, transactionId);
		transaction.updateComment(comment);
		transactionDataStore.save(transaction);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
