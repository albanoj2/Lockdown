package com.lockdown.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.domain.Budget;
import com.lockdown.domain.BudgetItem;
import com.lockdown.persist.store.BudgetDataStore;
import com.lockdown.persist.store.BudgetItemDataStore;
import com.lockdown.rest.error.ResourceNotFoundException;
import com.lockdown.rest.resource.BudgetItemResource;
import com.lockdown.rest.resource.assembler.BudgetItemResourceAssembler;

@RestController
@RequestMapping("/budget/{budgetId}")
@ExposesResourceFor(BudgetItem.class)
public class BudgetItemsController {

	@Autowired
	private BudgetDataStore budgetDataStore;
	
	@Autowired
	private BudgetItemDataStore budgetItemDataStore;
	
	@Autowired
	private BudgetItemResourceAssembler budgetItemAssembler;

	@GetMapping("/item")
	public ResponseEntity<List<BudgetItemResource>> getBudgetItems(@PathVariable String budgetId) {
		Budget budget = getBudgetIfExists(budgetId);
		return new ResponseEntity<>(budgetItemAssembler.toResources(budget.getItems()), HttpStatus.OK);
	}
	
	private Budget getBudgetIfExists(String id) {
		return budgetDataStore.findById(id)
			.orElseThrow(ResourceNotFoundException.supplierForResource("budget", id));
	}
	
	@PostMapping("/item")
	public ResponseEntity<BudgetItemResource> addBudgetItem(@PathVariable String budgetId, @RequestBody BudgetItem item) {
		Budget budget = getBudgetIfExists(budgetId);
		BudgetItem savedItem = budgetItemDataStore.saveAndCascade(item);
		budget.addBudgetItem(savedItem);
		budgetDataStore.saveAndCascade(budget);
		return new ResponseEntity<>(budgetItemAssembler.toResource(savedItem), HttpStatus.OK);
	}
	
	@DeleteMapping("/item/{itemId}")
	public ResponseEntity<Void> getBudgetItems(@PathVariable String budgetId, @PathVariable String itemId) {
		Budget budget = getBudgetIfExists(budgetId);
		boolean wasRemoved = budget.removeItemById(itemId);
		budgetDataStore.saveAndCascade(budget);
		
		if (wasRemoved) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/item/{itemId}")
	public ResponseEntity<BudgetItemResource> updateBudgetItems(@PathVariable String budgetId, @PathVariable String itemId, @RequestBody BudgetItem body) {
		BudgetItem updatedBudgetItem = budgetItemDataStore.saveAndCascade(body);
		return new ResponseEntity<>(budgetItemAssembler.toResource(updatedBudgetItem), HttpStatus.OK);
	}
}
