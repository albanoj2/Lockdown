package com.lockdown.persist.dto;

import java.time.LocalDate;
import java.util.Optional;

import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.Frequency;
import com.lockdown.domain.Money;

public class BudgetItemDto extends Dto {

	private String name;
	private final String description;
	private final long amountPerFrequencyInCents;
	private final String frequency;
	private final LocalDate start;
	private final LocalDate end;
	private final boolean isActive;

	public BudgetItemDto(BudgetItem budgetItem) {
		super(budgetItem.getId());
		this.name = budgetItem.getName();
		this.description = budgetItem.getDescription();
		this.amountPerFrequencyInCents = budgetItem.getAmountPerFrequency().asCents();
		this.frequency = budgetItem.getFrequency().name();
		this.start = budgetItem.getStart();
		this.end = budgetItem.getEnd().orElse(null);
		this.isActive = budgetItem.isActive();
	}
	
	public BudgetItemDto() {
		this.name = null;
		this.description = null;
		this.amountPerFrequencyInCents = 0;
		this.frequency = Frequency.NEVER.name();
		this.start = null;
		this.end = null;
		this.isActive = false;
	}
	
	public BudgetItem toBudgetItem() {
		return new BudgetItem(getId(), name, description, Money.cents(amountPerFrequencyInCents), Frequency.valueOf(frequency), start, Optional.ofNullable(end), isActive);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public long getAmountPerFrequencyInCents() {
		return amountPerFrequencyInCents;
	}

	public String getFrequency() {
		return frequency;
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public boolean isActive() {
		return isActive;
	}
}
