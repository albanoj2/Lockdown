package com.lockdown.rest.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.hateoas.ResourceSupport;

import com.lockdown.domain.Budget;
import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.BudgetItemSnapshot;
import com.lockdown.domain.BudgetSnapshot;

public class BudgetSnapshotResource extends ResourceSupport {

	private final BudgetResource budget;
	private final List<BudgetItemSnapshotResource> budgetItems;
	private final long totalAccumulated;
	private final long totalDeposited;
	private final long totalExpensed;
	private final long totalRemaining;
	
	
	public BudgetSnapshotResource(Budget budget, BudgetSnapshot snapshot) {
		this.budget = new BudgetResource(budget);
		this.budgetItems = toBudgetItemSnapshotResources(snapshot);
		this.totalAccumulated = snapshot.getTotalAccumulated().asCents();
		this.totalDeposited = snapshot.getTotalDeposited().asCents();
		this.totalExpensed = snapshot.getTotalExpensed().asCents();
		this.totalRemaining = snapshot.getTotalRemaining().asCents();
	}
	
	private static List<BudgetItemSnapshotResource> toBudgetItemSnapshotResources(BudgetSnapshot snapshot) {
		
		List<BudgetItemSnapshotResource> resources = new ArrayList<>();
		
		for (Entry<BudgetItem, BudgetItemSnapshot> entry: snapshot.getBudgetItemSnapshotsMap().entrySet()) {
			resources.add(new BudgetItemSnapshotResource(entry.getKey(), entry.getValue()));
		}
		
		return resources;
	}

	public BudgetResource getBudget() {
		return budget;
	}

	public List<BudgetItemSnapshotResource> getBudgetItems() {
		return budgetItems;
	}

	public long getTotalAccumulated() {
		return totalAccumulated;
	}

	public long getTotalDeposited() {
		return totalDeposited;
	}

	public long getTotalExpensed() {
		return totalExpensed;
	}

	public long getTotalRemaining() {
		return totalRemaining;
	}
}
