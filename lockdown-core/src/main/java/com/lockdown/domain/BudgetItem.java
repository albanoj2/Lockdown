package com.lockdown.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import com.lockdown.domain.util.Periods;

public class BudgetItem extends DomainObject {

	private String name;
	private final String description;
	private final Money amountPerFrequency;
	private final Period life;
	private final Frequency frequency;
	
	public BudgetItem(String id, String name, String description, Money amount, Period life, Frequency frequency) {
		super(id);
		this.name = name;
		this.description = description;
		this.amountPerFrequency = amount;
		this.life = life;
		this.frequency = frequency;
	}
	
	public BudgetItem() {
		this(null, "Unnamed", "", Money.zero(), Periods.fromNow(), Frequency.NEVER);
	}
	
	public static BudgetItem blank() {
		return new BudgetItem();
	}

	public Money getAmountPerFrequency() {
		return amountPerFrequency;
	}
	
	public Money getTotalAccumulatedAmount() {
		return amountPerFrequency.multiply(frequency.occurrencesIn(life));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description != null ? description : "";
	}

	public Period getLife() {
		return life;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	@Override
	public String toString() {
		String displayName = name != null ? name : "unnamed";
		return "Budget entry '" + displayName + "'";
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private Optional<String> id;
		private Optional<String> name;
		private Optional<String> description;
		private Money amount;
		private Frequency frequency;
		private Optional<Period> life;
		
		public Builder() {
			this.id = Optional.empty();
			this.name = Optional.empty();
			this.description = Optional.empty();
			this.life = Optional.empty();
		}
		
		public Builder id(String id) {
			this.id = Optional.of(id);
			return this;
		}
		
		public Builder name(String name) {
			this.name = Optional.ofNullable(name);
			return this;
		}
		
		public Builder description(String description) {
			this.description = Optional.ofNullable(description);
			return this;
		}
		
		public Builder amount(Money amount) {
			this.amount = amount;
			return this;
		}
		
		public Builder zeroAmount() {
			return amount(Money.zero());
		}
		
		public Builder frequency(Frequency frequency) {
			this.frequency = frequency;
			return this;
		}
		
		public Builder never() {
			return frequency(Frequency.NEVER);
		}
		
		public Builder weekly() {
			return frequency(Frequency.WEEKLY);
		}
		
		public Builder monthly() {
			return frequency(Frequency.MONTHLY);
		}
		
		public Builder life(Period life) {
			this.life = Optional.ofNullable(life);
			return this;
		}
		
		public Builder startingNow() {
			return life(Periods.fromNow());
		}
		
		public Builder start(LocalDate start) {
			return life(Periods.from(start));
		}
		
		public Builder with(Money amount, Frequency frequency) {
			return amount(amount)
				.frequency(frequency);
		}
		
		public BudgetItem build() {
			
			if (amount == null) throw new IllegalStateException("Amount must be provided");
			if (frequency == null) throw new IllegalStateException("Frequency must be provided");
			
			return new BudgetItem(
				id.orElse(null), 
				name.orElse("Unnamed"), 
				description.orElse(""), 
				amount, 
				life.orElse(Periods.fromNow()), 
				frequency
			);
		}
	}
}
