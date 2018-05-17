package com.lockdown.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Transaction extends DomainObject {

	private final LocalDate date;
	private final Money amount;
	private final String key;
	private final String name;
	private final String description;
	private final boolean isPending;
	private final Optional<BudgetItemMapping> budgetItemMapping;
	
	public Transaction(String id, LocalDate date, Money amount, String key, String name, String description, boolean isPending, Optional<BudgetItemMapping> budgetItemMapping) {
		super(id);
		this.date = date;
		this.amount = amount;
		this.key = key;
		this.name = name;
		this.description = description;
		this.isPending = isPending;
		this.budgetItemMapping = budgetItemMapping;
	}
	
	public static Transaction unbudgeted(String id, LocalDate date, Money amount, String key, String name, String description, boolean isPending) {
		return new Transaction(id, date, amount, key, name, description, isPending, Optional.empty());
	}
	
	public Transaction() {
		this(null, LocalDate.now(), Money.zero(), "", "Unnamed", "", false, Optional.empty());
	}

	public LocalDate getDate() {
		return date;
	}

	public Money getAmount() {
		return amount;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isPending() {
		return isPending;
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

	@Override
	public int hashCode() {
		return Objects.hash(key);
	}

	@Override
	public boolean equals(Object object) {
		
		if (this == object) {
			return true;
		}
		else if (!(object instanceof Transaction)) {
			return false;
		}
		else {
			Transaction other = (Transaction) object;
			return Objects.equals(getKey(), other.getKey());
		}
	}
}
