package com.lockdown.rest.resource.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
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
		TransactionResource resource = new TransactionResource(transaction);
		resource.add(getSelfLink(transaction));
		resource.add(getUpdateCommentLink(transaction));
		return resource;
	}
	
	public static Link getUpdateCommentLink(Transaction transaction) {
		return getSelf(transaction).slash("comment").withRel("updateComment");
	}
	
	public static ControllerLinkBuilder getSelf(Transaction transaction) {
		return linkTo(methodOn(TransactionsController.class).getTransaction(transaction.getId()));
	}
	
	public static Link getSelfLink(Transaction transaction) {
		return getSelf(transaction).withSelfRel();
	}
}
