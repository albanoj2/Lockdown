package com.lockdown.domain.account;

import java.time.LocalDate;
import java.util.Optional;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;

public class Transactions {
	
	private static BudgetItemMapping BLANK_MAPPING = (Transaction t, BudgetItem i) -> Money.zero();

	public static Transaction unbudgetedForAmount(Money amount) {
		return Transaction.unbudgeted("1", LocalDate.now(), amount, "Unnamed", "");
	}

	public static Transaction budgetedForAmount(Money amount) {
		return new Transaction("1", LocalDate.now(), amount, "Unnamed", "", Optional.of(BLANK_MAPPING));
	}

	public static Transaction budgetedForAmountWithMapping(Money amount, BudgetItemMapping mapping) {
		return new Transaction("1", LocalDate.now(), amount, "Unnamed", "", Optional.of(mapping));
	}
}

