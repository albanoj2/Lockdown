package com.lockdown.rest.resource.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Budget;
import com.lockdown.rest.controller.BudgetsController;
import com.lockdown.rest.resource.BudgetResource;

@Component
public class BudgetResourceAssembler extends ResourceAssemblerSupport<Budget, BudgetResource> {

	public BudgetResourceAssembler() {
		super(BudgetsController.class, BudgetResource.class);
	}

	@Override
	public BudgetResource toResource(Budget budget) {
		BudgetResource resource = new BudgetResource(budget);
		resource.add(getSelfLink(budget.getId()));
		return resource;
	}

	private static Link getSelfLink(String id) {
		return linkTo(methodOn(BudgetsController.class).getBudget(id)).withSelfRel();
	}
}
