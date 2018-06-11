package com.lockdown.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.domain.Account;
import com.lockdown.persist.store.AccountDataStore;
import com.lockdown.persist.store.TransactionDataStore;
import com.lockdown.rest.error.ResourceNotFoundException;
import com.lockdown.rest.resource.AccountResource;
import com.lockdown.rest.resource.TransactionResource;
import com.lockdown.rest.resource.assembler.AccountResourceAssembler;
import com.lockdown.rest.resource.assembler.TransactionResourceAssembler;

@RestController
@ExposesResourceFor(Account.class)
@RequestMapping("/account")
public class AccountsController {
	
	@Autowired
	private AccountDataStore accountDataStore;
	
	@Autowired
	private TransactionDataStore transactionDataStore;
	
	@Autowired
	private AccountResourceAssembler accountResourceAssembler;
	
	@Autowired
	private TransactionResourceAssembler transactionResourceAssembler;

	@GetMapping
	public ResponseEntity<List<AccountResource>> getAccounts() {
		List<Account> accounts = accountDataStore.findAll();
		return new ResponseEntity<>(accountResourceAssembler.toResources(accounts), HttpStatus.OK);
	}
	
	@GetMapping("/{accountId}")
	public ResponseEntity<AccountResource> getAccount(@PathVariable String accountId) {
		Account account = accountDataStore.findById(accountId)
			.orElseThrow(ResourceNotFoundException.supplierForResource("account", accountId));
		return new ResponseEntity<>(accountResourceAssembler.toResource(account), HttpStatus.OK);
	}
	
	@GetMapping("/{accountId}/transactions")
	public ResponseEntity<Page<TransactionResource>> getTransactions(@PathVariable String accountId, Pageable pageable) {
		List<String> transactionIds = accountDataStore.getTransactionIds(accountId);
		Page<TransactionResource> transactions = transactionDataStore.findByIdInOrderByDateDesc(transactionIds, pageable)
				.map(transaction -> transactionResourceAssembler.toResource(transaction));
		
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}
}
