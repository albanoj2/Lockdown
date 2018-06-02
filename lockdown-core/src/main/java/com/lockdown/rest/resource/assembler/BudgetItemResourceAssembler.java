package com.lockdown.rest.resource.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lockdown.domain.BudgetItem;
import com.lockdown.rest.controller.BudgetItemsController;
import com.lockdown.rest.resource.BudgetItemResource;

@Component
public class BudgetItemResourceAssembler extends ResourceAssemblerSupport<BudgetItem, BudgetItemResource> {

	public BudgetItemResourceAssembler() {
		super(BudgetItemsController.class, BudgetItemResource.class);
	}

	@Override
	public BudgetItemResource toResource(BudgetItem item) {
		return new BudgetItemResource(item);
	}

}
