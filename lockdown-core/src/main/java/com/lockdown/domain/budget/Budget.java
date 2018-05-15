package com.lockdown.domain.budget;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Budget {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private final Long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	private final List<BudgetItem> entries;
	
	public Budget(Long id, List<BudgetItem> entries) {
		this.id = id;
		this.entries = entries;
	}
	
	public Budget() {
		this(null, new ArrayList<>());
	}

	public static Budget empty() {
		return new Budget();
	}

	public Long getId() {
		return id;
	}

	public List<BudgetItem> getEntries() {
		return entries;
	}
}
