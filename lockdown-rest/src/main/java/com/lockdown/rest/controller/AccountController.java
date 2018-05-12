package com.lockdown.rest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.account.Account;
import com.lockdown.rest.resource.AccountResource;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@GetMapping
	public List<AccountResource> getAccounts() {
		return List.of(new AccountResource(Account.blank()));
	}
	
	@GetMapping("/{id}")
	public AccountResource getAccount(@PathVariable long id) {
		return new AccountResource(Account.blank());
	}
}
