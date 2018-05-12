package com.lockdown.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.account.Account;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@GetMapping
	public Account getAccounts() {
		return Account.blank();
	}
}
