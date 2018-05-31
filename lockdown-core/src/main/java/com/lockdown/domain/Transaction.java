package com.lockdown.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class Transaction extends Identifiable {

	private final String key;
	private TransactionBody body;
	private Optional<String> comment;
	private Optional<BudgetItemMapping> budgetItemMapping;
	
	public Transaction(String id, LocalDate date, Money amount, String key, String name, String description, boolean isPending, Optional<String> comment, Optional<BudgetItemMapping> budgetItemMapping) {
		this(id, key, new TransactionBody(date, amount, name, description, isPending), comment, budgetItemMapping);
	}
	
	public Transaction(String id, String key, TransactionBody body, Optional<String> comment, Optional<BudgetItemMapping> budgetItemMapping) {
		super(id);
		this.key = key;
		this.body = body;
		this.comment = comment;
		this.budgetItemMapping = budgetItemMapping;
	}
	
	public static Transaction unbudgeted(String id, LocalDate date, Money amount, String key, String name, String description, boolean isPending, Optional<String> comment) {
		return new Transaction(id, date, amount, key, name, description, isPending, comment, noMapping());
	}
	
	public static Optional<BudgetItemMapping> noMapping() {
		return Optional.empty();
	}
	
	public Transaction() {
		this(null, LocalDate.now(), Money.zero(), "", "Unnamed", "", false, Optional.empty(), Optional.empty());
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
	
	boolean bodyEquals(TransactionBody other) {
		return body.equals(other);
	}
	
	public Optional<String> getComment() {
		return comment;
	}
	
	public void updateComment(String comment) {
		this.comment = Optional.ofNullable(comment);
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
	
	public void mapTo(BudgetItem item) {
		BudgetItemMapping mapping = BudgetItemMapping.withMapping(item, getAmount());
		this.budgetItemMapping = Optional.of(mapping);
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
