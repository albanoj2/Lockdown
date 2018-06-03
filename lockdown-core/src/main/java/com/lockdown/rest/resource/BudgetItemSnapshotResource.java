package com.lockdown.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.BudgetItemSnapshot;

public class BudgetItemSnapshotResource extends ResourceSupport {

	private final BudgetItemResource budgetItem;
	private final long accumulated;
	private final long deposited;
	private final long expensed;
	private final long remaining;
	
	public BudgetItemSnapshotResource(BudgetItem item, BudgetItemSnapshot snapshot) {
		this.budgetItem = new BudgetItemResource(item);
		this.accumulated = snapshot.getAccumulatedAmount().asCents();
		this.deposited = snapshot.getDepositedAmount().asCents();
		this.expensed = snapshot.getExpensedAmount().asCents();
		this.remaining = snapshot.getRemainingAmount().asCents();
	}

	public BudgetItemResource getBudgetItem() {
		return budgetItem;
	}

	public long getAccumulated() {
		return accumulated;
	}

	public long getDeposited() {
		return deposited;
	}

	public long getExpensed() {
		return expensed;
	}

	public long getRemaining() {
		return remaining;
	}
}
