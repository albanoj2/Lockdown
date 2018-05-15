package com.lockdown.domain.budget;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.lockdown.domain.money.Money;
import com.lockdown.domain.money.MoneyAttributeConverter;
import com.lockdown.domain.time.Periods;

@Entity
public class BudgetItem {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private final Long id;
	private String name;
	private final String description;
	@Convert(converter = MoneyAttributeConverter.class)
	private final Money amountPerFrequency;
	private final Frequency frequency;
	private final Period life;
	
	private BudgetItem(Long id, String name, String description, Money amount, Period life, Frequency frequency) {
		this.id = id;
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

	public Long getId() {
		return id;
	}

	public Period getLife() {
		return life;
	}

	public Money getAmountPerFrequency() {
		return amountPerFrequency;
	}

	public Frequency getFrequency() {
		return frequency;
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

	@Override
	public String toString() {
		String displayName = name != null ? name : "unnamed";
		return "Budget entry '" + displayName + "'";
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private Optional<Long> id;
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
		
		public Builder id(Long id) {
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
