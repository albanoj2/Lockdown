package com.lockdown.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public class BudgetItem extends Identifiable {

	private final String name;
	private final String description;
	private final Money amountPerFrequency;
	private final Frequency frequency;
	private LocalDate start;
	private Optional<LocalDate> end;
	private boolean isActive;

	public BudgetItem(String id, String name, String description, Money amount, Frequency frequency, LocalDate start,
			Optional<LocalDate> end, boolean isActive) {
		super(id);

		if (end.isPresent() && end.get().isBefore(start)) {
			throw new IllegalArgumentException("End date cannot be before start date");
		} else if (!isActive && !end.isPresent()) {
			throw new IllegalArgumentException("Inactive budget item must have an end date");
		}

		this.name = name;
		this.description = description;
		this.amountPerFrequency = amount;
		this.frequency = frequency;
		this.start = start;
		this.end = end;
		this.isActive = isActive;
	}

	public BudgetItem() {
		this(null, "Unnamed", "", Money.zero(), Frequency.NEVER, LocalDate.now(), Optional.empty(), true);
	}

	public static BudgetItem blank() {
		return new BudgetItem();
	}

	public static BudgetItem withName(String name) {
		return new BudgetItem(null, name, "", Money.zero(), Frequency.NEVER, LocalDate.now(), Optional.empty(), true);
	}

	public Money getAmountPerFrequency() {
		return amountPerFrequency;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description != null ? description : "";
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public Optional<LocalDate> getEnd() {
		return end;
	}

	public void setEnd(Optional<LocalDate> end) {
		this.end = end;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Period getAccumulationPeriod() {

		if (isActive) {
			return Period.between(start, LocalDate.now());
		} else {
			return Period.between(start, end.get());
		}
	}

	public Money getTotalAccumulatedAmount() {
		return amountPerFrequency.multiply(occurrencesThusFar());
	}

	private int occurrencesThusFar() {
		return frequency.occurrencesIn(getAccumulationPeriod());
	}

	@Override
	public String toString() {
		String displayName = name != null ? name : "unnamed";
		return "Budget entry '" + displayName + "'";
	}
}
