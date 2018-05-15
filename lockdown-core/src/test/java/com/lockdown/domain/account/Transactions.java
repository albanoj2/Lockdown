package com.lockdown.domain.account;

import java.time.LocalDate;
import java.util.Optional;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;

public class Transactions {
	
	public static BudgetItemMapping BLANK_MAPPING = new BlankBudgetItemMapping();

	public static Transaction unbudgetedForAmount(Money amount) {
		return Transaction.unbudgeted(1L, LocalDate.now(), amount, "Unnamed", "");
	}

	public static Transaction budgetedForAmount(Money amount) {
		return new Transaction(1L, LocalDate.now(), amount, "Unnamed", "", Optional.of(BLANK_MAPPING));
	}

	public static Transaction budgetedForAmountWithMapping(Money amount, BudgetItemMapping mapping) {
		return new Transaction(1L, LocalDate.now(), amount, "Unnamed", "", Optional.of(mapping));
	}
	
	private static class BlankBudgetItemMapping extends BudgetItemMapping {

		protected BlankBudgetItemMapping() {
			super(null);
		}

		@Override
		public Money amountFor(Transaction transaction, BudgetItem item) {
			return Money.zero();
		}
	}
}

