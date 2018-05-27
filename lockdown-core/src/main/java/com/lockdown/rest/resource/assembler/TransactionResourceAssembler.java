package com.lockdown.rest.resource.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lockdown.rest.controller.TransactionsController;
import com.lockdown.rest.controller.model.TransactionModel;
import com.lockdown.rest.resource.TransactionResource;

@Component
public class TransactionResourceAssembler extends ResourceAssemblerSupport<TransactionModel, TransactionResource> {
	
	public TransactionResourceAssembler() {
		super(TransactionsController.class, TransactionResource.class);
	}

	public TransactionResource toResource(TransactionModel model) {
		TransactionResource resource = new TransactionResource(model.getTransaction());
		resource.add(model.getPortfolioLink());
		resource.add(model.getAccountLink());
		resource.add(model.getSelfLink());
		resource.add(model.getUpdateCommentLink());
		return resource;
	}
}
