package com.lockdown.budget;

import java.time.LocalDate;
import java.util.Optional;

import com.lockdown.DomainObject;
import com.lockdown.money.DollarAmount;

public class BudgetEntry extends DomainObject {

	private String name;
	private String description;
	private final DollarAmount amount;
	private final LocalDate start;
	private Optional<LocalDate> stop;
	private final Frequency frequency;
	
	public BudgetEntry(long id, DollarAmount amount, LocalDate start, LocalDate stop, Frequency frequency) {
		this(id, amount, start, Optional.of(stop), frequency);
	}
	
	private BudgetEntry(long id, DollarAmount amount, LocalDate start, Optional<LocalDate> stop, Frequency frequency) {
		super(id);
		this.amount = amount;
		this.start = start;
		this.stop = stop;
		this.frequency = frequency;
	}
	
	public BudgetEntry(long id, DollarAmount amount, LocalDate start, Frequency frequency) {
		this(id, amount, start, Optional.empty(), frequency);
	}
	
	public BudgetEntry(DollarAmount amount, LocalDate start, LocalDate stop, Frequency frequency) {
		this(amount, start, Optional.of(stop), frequency);
	}
	
	private BudgetEntry(DollarAmount amount, LocalDate start, Optional<LocalDate> stop, Frequency frequency) {
		this.amount = amount;
		this.start = start;
		this.stop = stop;
		this.frequency = frequency;
	}
	
	public BudgetEntry(DollarAmount amount, LocalDate start, Frequency frequency) {
		this(amount, start, Optional.empty(), frequency);
	}

	public DollarAmount getAmount() {
		return amount;
	}

	public Frequency getFrequency() {
		return frequency;
	}
	
	public static BudgetEntry startingNow(long id, DollarAmount amount, Frequency frequency) {
		return new BudgetEntry(id, amount, LocalDate.now(), frequency);
	}
	
	public static BudgetEntry startingNow(DollarAmount amount, Frequency frequency) {
		return new BudgetEntry(amount, LocalDate.now(), frequency);
	}
	
	public static BudgetEntry none() {
		return startingNow(0, DollarAmount.zero(), FrequencyUnits.NEVER);
	}

	public LocalDate getStart() {
		return start;
	}
	
	public boolean isActive() {
		return !isStopped();
	}
	
	public boolean isStopped() {
		return stop.isPresent();
	}
	
	public void setStop(LocalDate date) {
		this.stop = Optional.of(date);
	}
	
	public Optional<LocalDate> getStop() {
		return stop;
	}
	
	public LocalDate getStopOrNow() {
		return stop.orElse(LocalDate.now());
	}
	
	public DollarAmount getTotalAccumulatedAmount() {
		return amount.multiply(frequency.occurrencesBetween(start, getStopOrNow()));
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

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		String displayName = name != null ? name : "unnamed";
		return "Budget entry '" + displayName + "'";
	}
}
