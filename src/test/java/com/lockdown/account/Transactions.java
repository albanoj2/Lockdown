package com.lockdown.account;

import com.lockdown.budget.BudgetEntry;
import com.lockdown.money.Money;

public class Transactions {

	public static UnbudgetedTransaction unbudgetedForAmount(Money amount) {
		return UnbudgetedTransaction.now("Unnamed", "Unknown", amount);
	}

	public static BudgetedTransaction budgetedForAmount(Money amount) {
		return Transactions.budgetedForAmountWithMapping(amount, BlankBudgetEntryMapping.get());
	}

	public static BudgetedTransaction budgetedForAmountWithMapping(Money amount, BudgetEntryMapping mapping) {
		return BudgetedTransaction.now("Unnamed", "Unknown", amount, mapping);
	}

	public static class BlankBudgetEntryMapping implements BudgetEntryMapping {

		public static BlankBudgetEntryMapping get() {
			return new BlankBudgetEntryMapping();
		}

		@Override
		public Money amountFor(Transaction budgetedTransaction, BudgetEntry entry) {
			return Money.zero();
		}

	}
}
