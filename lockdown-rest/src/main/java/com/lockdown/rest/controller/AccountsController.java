package com.lockdown.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.account.Account;
import com.lockdown.persist.mongo.AccountRepository;
import com.lockdown.rest.controller.error.InvalidAccountException;
import com.lockdown.rest.resource.AccountResource;
import com.lockdown.rest.resource.AccountsResource;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
	
	@Autowired
	private AccountRepository repository;

	@GetMapping
	public ResponseEntity<AccountsResource> getAccounts() {
		List<Account> accounts = repository.findAll();
		return new ResponseEntity<>(AccountsResource.from(accounts), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountResource> getAccount(@PathVariable long id) {
		Optional<Account> account = repository.findById(id);
		
		if (account.isPresent()) {
			return ResponseEntity.ok(AccountResource.from(account.get()));
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<AccountResource> createAccount(@RequestBody Account accountToCreate) {
		validate(accountToCreate);
		Account createdAccount = repository.insert(accountToCreate);
		return new ResponseEntity<>(AccountResource.from(createdAccount), HttpStatus.OK);
	}
	
	private static void validate(Account account) {
		
		if (account.getName() == null) {
			throw new InvalidAccountException("Name cannot be null");
		}
	}
}
