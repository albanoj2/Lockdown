package com.lockdown.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class Transaction extends Identifable {

	private final String key;
	private TransactionBody body;
	private final Optional<BudgetItemMapping> budgetItemMapping;
	
	public Transaction(String id, LocalDate date, Money amount, String key, String name, String description, boolean isPending, Optional<BudgetItemMapping> budgetItemMapping) {
		this(id, key, new TransactionBody(date, amount, name, description, isPending), budgetItemMapping);
	}
	
	public Transaction(String id, String key, TransactionBody body, Optional<BudgetItemMapping> budgetItemMapping) {
		super(id);
		this.key = key;
		this.body = body;
		this.budgetItemMapping = budgetItemMapping;
	}
	
	public static Transaction unbudgeted(String id, LocalDate date, Money amount, String key, String name, String description, boolean isPending) {
		return new Transaction(id, date, amount, key, name, description, isPending, noMapping());
	}
	
	public static Optional<BudgetItemMapping> noMapping() {
		return Optional.empty();
	}
	
	public Transaction() {
		this(null, LocalDate.now(), Money.zero(), "", "Unnamed", "", false, Optional.empty());
	}

	public LocalDate getDate() {
		return body.getDate();
	}

	public Money getAmount() {
		return body.getAmount();
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return body.getName();
	}

	public String getDescription() {
		return body.getDescription();
	}

	public boolean isPending() {
		return body.isPending();
	}
	
	TransactionBody getBody() {
		return body;
	}

	@JsonIgnore
	public Optional<BudgetItemMapping> getBudgetItemMapping() {
		return budgetItemMapping;
	}
	
	public boolean isBudgeted() {
		return budgetItemMapping.isPresent();
	}
	
	public synchronized void updateBody(TransactionBody body) {
		this.body = body;
	}
	
	@JsonIgnore
	public boolean isUnbudgeted() {
		return !isBudgeted();
	}
	
	@JsonIgnore
	public boolean isExpense() {
		return getAmount().isNegative();
	}

	@JsonIgnore
	public boolean isDeposit() {
		return getAmount().isPositive();
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

	@Override
	public String toString() {
		return "Transaction [date=" + getDate() + ", amount=" + getAmount() + ", key=" + key + ", name=" + getName() + ", description="
				+ getDescription() + ", isPending=" + isPending() + ", budgetItemMapping=" + budgetItemMapping + "]";
	}
}
