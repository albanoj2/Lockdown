package com.lockdown.domain.account;

import com.lockdown.domain.account.BudgetEntryMapping;
import com.lockdown.domain.account.BudgetedTransaction;
import com.lockdown.domain.account.Transaction;
import com.lockdown.domain.account.UnbudgetedTransaction;
import com.lockdown.domain.budget.BudgetEntry;
import com.lockdown.domain.money.Money;

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
