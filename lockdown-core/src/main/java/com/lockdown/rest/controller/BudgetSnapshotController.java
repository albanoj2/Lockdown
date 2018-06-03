package com.lockdown.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.domain.Account;
import com.lockdown.domain.Budget;
import com.lockdown.domain.BudgetSnapshot;
import com.lockdown.persist.store.AccountDataStore;
import com.lockdown.persist.store.BudgetDataStore;
import com.lockdown.rest.controller.util.Retriever;
import com.lockdown.rest.resource.BudgetSnapshotResource;

@RestController
@RequestMapping("/budget/{id}/snapshot")
@ExposesResourceFor(BudgetSnapshot.class)
public class BudgetSnapshotController {
	
	@Autowired
	private BudgetDataStore budgetDataStore;
	
	@Autowired
	private AccountDataStore accountDataStore;
	
	@GetMapping
	public ResponseEntity<BudgetSnapshotResource> getSnapshot(@PathVariable String id) {
		Budget budget = Retriever.getBudgetIfExists(id, budgetDataStore);
		List<Account> accounts = accountDataStore.findAll();
		BudgetSnapshot snapshot = new BudgetSnapshot(budget, accounts);
		return new ResponseEntity<>(new BudgetSnapshotResource(budget, snapshot), HttpStatus.OK);
	}
}
