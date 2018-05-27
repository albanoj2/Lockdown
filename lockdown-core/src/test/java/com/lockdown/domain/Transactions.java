package com.lockdown.domain;

import java.time.LocalDate;
import java.util.Optional;

import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.BudgetItemMapping;
import com.lockdown.domain.Money;
import com.lockdown.domain.Transaction;

public class Transactions {
	
	private static final Optional<String> NO_COMMENT = Optional.empty();
	private static final BudgetItemMapping RAW_BLANK_MAPPING = (Transaction t, BudgetItem i) -> Money.zero();
	public static final Optional<BudgetItemMapping> BLANK_MAPPING = Optional.of(RAW_BLANK_MAPPING);

	public static Transaction unbudgetedForAmount(Money amount) {
		return Transaction.unbudgeted("1", LocalDate.now(), amount, "foo", "Unnamed", "", false, NO_COMMENT);
	}

	public static Transaction budgetedForAmount(Money amount) {
		return new Transaction("1", LocalDate.now(), amount, "foo", "Unnamed", "", false, NO_COMMENT, BLANK_MAPPING);
	}

	public static Transaction budgetedForAmountWithMapping(Money amount, BudgetItemMapping mapping) {
		return new Transaction("1", LocalDate.now(), amount, "foo", "Unnamed", "", false, NO_COMMENT, Optional.of(mapping));
	}
	
	public static Transaction withKey(String key) {
		return withIdAndKey("1", key);
	}
	
	public static Transaction withIdAndKey(String id, String key) {
		return new Transaction(id, LocalDate.now(), Money.zero(), key, "Unnamed", "", false, NO_COMMENT, Optional.empty());
	}
}

