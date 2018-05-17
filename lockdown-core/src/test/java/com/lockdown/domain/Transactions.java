package com.lockdown.domain;

import java.time.LocalDate;
import java.util.Optional;

import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.BudgetItemMapping;
import com.lockdown.domain.Money;
import com.lockdown.domain.Transaction;

public class Transactions {
	
	private static BudgetItemMapping BLANK_MAPPING = (Transaction t, BudgetItem i) -> Money.zero();

	public static Transaction unbudgetedForAmount(Money amount) {
		return Transaction.unbudgeted("1", LocalDate.now(), amount, "", "Unnamed", "", false);
	}

	public static Transaction budgetedForAmount(Money amount) {
		return new Transaction("1", LocalDate.now(), amount, "", "Unnamed", "", false, Optional.of(BLANK_MAPPING));
	}

	public static Transaction budgetedForAmountWithMapping(Money amount, BudgetItemMapping mapping) {
		return new Transaction("1", LocalDate.now(), amount, "", "Unnamed", "", false, Optional.of(mapping));
	}
	
	public static Transaction withKey(String key) {
		return new Transaction("1", LocalDate.now(), Money.zero(), key, "Unnamed", "", false, Optional.empty());
	}
}

