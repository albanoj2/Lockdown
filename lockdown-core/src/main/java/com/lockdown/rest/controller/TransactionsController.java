package com.lockdown.rest.controller;

import java.util.List;

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

import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.Transaction;
import com.lockdown.persist.store.BudgetItemDataStore;
import com.lockdown.persist.store.TransactionDataStore;
import com.lockdown.rest.controller.util.Retriever;
import com.lockdown.rest.resource.SingleBudgetItemMappingResource;
import com.lockdown.rest.resource.TransactionResource;
import com.lockdown.rest.resource.assembler.TransactionResourceAssembler;

@RestController
@ExposesResourceFor(Transaction.class)
@RequestMapping("/transaction")
public class TransactionsController {
	
	@Autowired
	private TransactionDataStore transactionDataStore;
	
	@Autowired
	private TransactionResourceAssembler assembler;
	
	@Autowired
	private BudgetItemDataStore budgetItemDataStore;

	@GetMapping
	public ResponseEntity<List<TransactionResource>> getTransactions(@PathVariable String accountId) {
		List<Transaction> transactions = transactionDataStore.findAll();
		return new ResponseEntity<>(assembler.toResources(transactions), HttpStatus.OK);
	}
	
	@GetMapping("/{transactionId}")
	public ResponseEntity<TransactionResource> getTransaction(@PathVariable String transactionId) {
		Transaction transaction = getTransactionFromDataStore(transactionId);
		return new ResponseEntity<>(assembler.toResource(transaction), HttpStatus.OK);
	}

	private Transaction getTransactionFromDataStore(String transactionId) {
		return Retriever.getTransactionIfExists(transactionId, transactionDataStore);
	}
	
	@PatchMapping("/{transactionId}/comment")
	public ResponseEntity<?> addComment(@PathVariable String transactionId, @RequestBody String comment) {
		Transaction transaction = getTransactionFromDataStore(transactionId);
		transaction.updateComment(comment);
		transactionDataStore.save(transaction);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping("/{transactionId}/mapping/single")
	public ResponseEntity<TransactionResource> setMappingToSingleBudgetItem(@PathVariable String transactionId, @RequestBody SingleBudgetItemMappingResource mapping) {
		Transaction transaction = getTransactionFromDataStore(transactionId);
		BudgetItem budgetItem = Retriever.getBudgetItemIfExists(mapping.getBudgetItemId(), budgetItemDataStore);
		transaction.mapTo(budgetItem);
		Transaction updatedTransaction = transactionDataStore.saveAndCascade(transaction);
		return new ResponseEntity<>(assembler.toResource(updatedTransaction), HttpStatus.OK);
	}
}
