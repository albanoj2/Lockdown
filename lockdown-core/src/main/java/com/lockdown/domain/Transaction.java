package com.lockdown.domain;

import java.time.LocalDate;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Transaction extends DomainObject {

	private final LocalDate date;
	private final Money amount;
	private final String name;
	private final String description;
	private final Optional<BudgetItemMapping> budgetItemMapping;
	
	public Transaction(String id, LocalDate date, Money amount, String name, String description, Optional<BudgetItemMapping> budgetItemMapping) {
		super(id);
		this.date = date;
		this.amount = amount;
		this.name = name;
		this.description = description;
		this.budgetItemMapping = budgetItemMapping;
	}
	
	public static Transaction unbudgeted(String id, LocalDate date, Money amount, String name, String description) {
		return new Transaction(id, date, amount, name, description, Optional.empty());
	}
	
	public Transaction() {
		this(null, LocalDate.now(), Money.zero(), "Unnamed", "", Optional.empty());
	}

	public LocalDate getDate() {
		return date;
	}

	public Money getAmount() {
		return amount;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@JsonIgnore
	public Optional<BudgetItemMapping> getBudgetItemMapping() {
		return budgetItemMapping;
	}
	
	public boolean isBudgeted() {
		return budgetItemMapping.isPresent();
	}
	
	@JsonIgnore
	public boolean isUnbudgeted() {
		return !isBudgeted();
	}
	
	@JsonIgnore
	public boolean isExpense() {
		return amount.isNegative();
	}

	@JsonIgnore
	public boolean isDeposit() {
		return amount.isPositive();
	}
	
	@JsonIgnore
	public boolean isValid() {
		
		if (isBudgeted()) {
			return budgetItemMapping.get().isValidFor(this);
		}
		else {
			return true;
		}
	}
	
	public Money amountFor(BudgetItem entry) {
		
		if (budgetItemMapping.isPresent()) {
			return budgetItemMapping.get().amountFor(this, entry);
		}
		else {
			return Money.zero();
		}
	}
}
