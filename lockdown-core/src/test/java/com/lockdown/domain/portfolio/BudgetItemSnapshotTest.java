package com.lockdown.domain.portfolio;

import static com.lockdown.domain.money.Money.dollars;
import static org.junit.Assert.assertEquals;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.lockdown.domain.account.BudgetItemMapping;
import com.lockdown.domain.account.SingleBudgetItemMapping;
import com.lockdown.domain.account.Transaction;
import com.lockdown.domain.account.Transactions;
import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;

public class BudgetItemSnapshotTest {

	@Test
	public void noTransactionsEnsureExpensedAmountIsZero() {
		assertEquals(Money.zero(), emptySnapshot().getExpensedAmount());
	}

	private static BudgetItemSnapshot emptySnapshot() {
		return new BudgetItemSnapshot(BudgetItem.blank(), new ArrayList<>());
	}
	
	@Test
	public void noTransactionsEnsureDepositedAmountIsZero() {
		assertEquals(Money.zero(), emptySnapshot().getDepositedAmount());
	}
	
	@Test
	public void singleNegativeTransactionEnsureCorrectExpensedAmount() {
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(List.of(-3));
		assertEquals(Money.dollars(3), snapshot.getExpensedAmount());
	}
	
	private static BudgetItemSnapshot createSnapshotForTransactionAmounts(List<Integer> amounts) {
		BudgetItem entry = createBlankEntry();
		BudgetItemMapping mapping = createMappingFor(entry);
		return createSnapshotForTransactionAmounts(entry, mapping, amounts);
	}
	
	private static BudgetItemSnapshot createSnapshotForTransactionAmounts(BudgetItem entry, BudgetItemMapping mapping, List<Integer> amounts) {
		List<Transaction> budgetedTransactions = amounts.stream()
			.map(amount -> Transactions.budgetedForAmountWithMapping(Money.dollars(amount), mapping))
			.collect(Collectors.toList());
		
		return new BudgetItemSnapshot(entry, budgetedTransactions);
	}
	
	private static BudgetItem createBlankEntry() {
		return createEntry(0);
	}
	
	private static BudgetItem createEntry(int dollars) {
		return BudgetItem.builder()
			.amount(Money.dollars(dollars))
			.startingNow()
			.weekly()
			.build();
	}
	
	private static BudgetItemMapping createMappingFor(BudgetItem entry) {
		return new SingleBudgetItemMapping(entry);
	}
	
	@Test
	public void twoNegativeTransactionsEnsureCorrectExpensedAmount() {
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(List.of(-3, -7));
		assertEquals(Money.dollars(10), snapshot.getExpensedAmount());
	}
	
	@Test
	public void oneNegativeOnePositiveTransactionEnsureCorrectExpensedAmount() {
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3, -7));
		assertEquals(Money.dollars(7), snapshot.getExpensedAmount());
	}
	
	@Test
	public void twoNegativeTransactionsWithFixedMappingAmountEnsureCorrectExpensedAmount() {
		BudgetItem entry = createBlankEntry();
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(entry, (t, e) -> Money.zero(), List.of(-3, -7));
		assertEquals(Money.zero(), snapshot.getExpensedAmount());
	}

	@Test
	public void twoNegativeTransactionsWithOneNotMappedToBudgetEntryEnsureCorrectExpensedAmount() {
		BudgetItem entry = createBlankEntry();
		Transaction mappedTransaction = createMappedTransaction(dollars(-3), entry);
		Transaction unmappedTransaction = createUnmappedTransaction(dollars(-3));
		BudgetItemSnapshot snapshot = new BudgetItemSnapshot(entry, List.of(mappedTransaction, unmappedTransaction));
		assertEquals(dollars(3), snapshot.getExpensedAmount());
	}
	
	private static Transaction createMappedTransaction(Money amount, BudgetItem entry) {
		return Transactions.budgetedForAmountWithMapping(amount, createMappingFor(entry));
	}
	
	private static Transaction createUnmappedTransaction(Money amount) {
		return Transactions.budgetedForAmount(amount);
	}
	
	@Test
	public void singlePositiveTransactionEnsureCorrectDepositedAmount() {
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3));
		assertEquals(Money.dollars(3), snapshot.getDepositedAmount());
	}
	
	@Test
	public void twoPositiveTransactionsEnsureCorrectDepositedAmount() {
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3, 7));
		assertEquals(Money.dollars(10), snapshot.getDepositedAmount());
	}
	
	@Test
	public void oneNegativeOnePositiveTransactionEnsureCorrectDepositedAmount() {
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3, -7));
		assertEquals(Money.dollars(3), snapshot.getDepositedAmount());
	}
	
	@Test
	public void twoPositiveTransactionsWithFixedMappingAmountEnsureCorrectDepositedAmount() {
		BudgetItem entry = createBlankEntry();
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(entry, (t, e) -> Money.zero(), List.of(3, 7));
		assertEquals(Money.zero(), snapshot.getDepositedAmount());
	}

	@Test
	public void twoPositiveTransactionsWithOneNotMappedToBudgetEntryEnsureCorrectDepositedAmount() {
		BudgetItem entry = createBlankEntry();
		Transaction mappedTransaction = createMappedTransaction(dollars(3), entry);
		Transaction unmappedTransaction = createUnmappedTransaction(dollars(3));
		BudgetItemSnapshot snapshot = new BudgetItemSnapshot(entry, List.of(mappedTransaction, unmappedTransaction));
		assertEquals(dollars(3), snapshot.getDepositedAmount());
	}
	
	@Test
	public void cononicalTransactionsEnsureCorrectRemainingAmount() {
		BudgetItem entry = tenDollarsEachWeekForTwoWeeks();
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(entry, new SingleBudgetItemMapping(entry), List.of(5, -10, 30, -20));
		
		// Accumulated amount:    $10/week * 2 weeks     = $20
		// Transaction total:     $5 - $10 + $30 - $20   = $5
		// Total:                 $5 + $20               = $25
		assertEquals(Money.dollars(25), snapshot.getRemainingAmount());
	}
	
	public static BudgetItem tenDollarsEachWeekForTwoWeeks() {
		return BudgetItem.builder()
			.amount(dollars(10))
			.life(Period.ofWeeks(2))
			.weekly()
			.build();
	}
	
	@Test
	public void cononicalTransactionsWithExpectedNegativeValueEnsureCorrectRemainingAmount() {
		BudgetItem entry = tenDollarsEachWeekForTwoWeeks();
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(entry, new SingleBudgetItemMapping(entry), List.of(5, -10, -30, -20));
		
		// Accumulated amount:    $10/week * 2 weeks     = $20
		// Transaction total:     $5 - $10 - $30 - $20   = -$55
		// Total:                 -$55 + $20               = -$35
		assertEquals(Money.dollars(-35), snapshot.getRemainingAmount());
	}
}
