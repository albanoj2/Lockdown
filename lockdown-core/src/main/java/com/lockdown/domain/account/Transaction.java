package com.lockdown.domain.account;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;
import com.lockdown.domain.money.MoneyAttributeConverter;

@Entity
public class Transaction {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private final Long id;
	private final LocalDate date;
	@Convert(converter = MoneyAttributeConverter.class)
	private final Money amount;
	private final String name;
	private final String description;
	
	@OneToOne
	private final BudgetItemMapping budgetItemMapping;
	
	public Transaction(Long id, LocalDate date, Money amount, String name, String description, Optional<BudgetItemMapping> budgetItemMapping) {
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.name = name;
		this.description = description;
		this.budgetItemMapping = budgetItemMapping.orElse(null);
	}
	
	public static Transaction unbudgeted(Long id, LocalDate date, Money amount, String name, String description) {
		return new Transaction(id, date, amount, name, description, Optional.empty());
	}
	
	public Transaction() {
		this(null, LocalDate.now(), Money.zero(), "Unnamed", "", Optional.empty());
	}

	public Long getId() {
		return id;
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
		return Optional.ofNullable(budgetItemMapping);
	}
	
	public boolean isBudgeted() {
		return getBudgetItemMapping().isPresent();
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
			return getBudgetItemMapping().get().isValidFor(this);
		}
		else {
			return true;
		}
	}
	
	public Money amountFor(BudgetItem entry) {
		
		Optional<BudgetItemMapping> mapping = getBudgetItemMapping();
		
		if (mapping.isPresent()) {
			return mapping.get().amountFor(this, entry);
		}
		else {
			return Money.zero();
		}
	}
}
