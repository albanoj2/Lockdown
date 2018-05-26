package com.lockdown.rest.resource.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Transaction;
import com.lockdown.rest.controller.TransactionsController;
import com.lockdown.rest.resource.TransactionResource;

@Component
public class TransactionResourceAssembler extends ResourceAssemblerSupport<Transaction, TransactionResource> {
	
	public TransactionResourceAssembler() {
		super(TransactionsController.class, TransactionResource.class);
	}

	public TransactionResource toResource(Transaction transaction) {
		return new TransactionResource(transaction);
	}

}
