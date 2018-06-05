package com.lockdown.domain;

import static com.lockdown.domain.Money.dollars;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;

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
		return createSnapshotForTransactionAmounts(entry, amounts);
	}
	
	private static BudgetItemSnapshot createSnapshotForTransactionAmounts(BudgetItem entry, BudgetItemMapping mapping, List<Integer> amounts) {
		List<Transaction> budgetedTransactions = amounts.stream()
			.map(amount -> Transactions.budgetedForAmountWithMapping(Money.dollars(amount), mapping))
			.collect(Collectors.toList());
		
		return new BudgetItemSnapshot(entry, budgetedTransactions);
	}
	
	private static BudgetItemSnapshot createSnapshotForTransactionAmounts(BudgetItem entry, List<Integer> amounts) {
		List<Transaction> budgetedTransactions = amounts.stream()
			.map(amount -> Transactions.budgetedForAmountWithMapping(Money.dollars(amount), BudgetItemMapping.withMapping(entry, Money.dollars(amount))))
			.collect(Collectors.toList());
		
		return new BudgetItemSnapshot(entry, budgetedTransactions);
	}
	
	private static BudgetItem createBlankEntry() {
		return createEntry(0);
	}
	
	private static BudgetItem createEntry(int dollars) {
		return new BudgetItem(null, "foo", "bar", Money.dollars(dollars), Frequency.WEEKLY, LocalDate.now(), Optional.empty(), true);
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
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(entry, BudgetItemMapping.blank(), List.of(-3, -7));
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
		return Transactions.budgetedForAmountWithMapping(amount, BudgetItemMapping.withMapping(entry, amount));
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
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(entry, BudgetItemMapping.blank(), List.of(3, 7));
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
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(entry, List.of(5, -10, 30, -20));
		
		// Accumulated amount:    $10/week * 3 weeks     = $30
		//	     3 weeks because of this week + plus 2
		// Transaction total:     $5 - $10 + $30 - $20   = $5
		// Total:                 $5 + $20               = $25
		assertEquals(Money.dollars(35), snapshot.getRemainingAmount());
	}
	
	public static BudgetItem tenDollarsEachWeekForTwoWeeks() {
		LocalDate twoWeeksAgo = LocalDate.now().minus(2, ChronoUnit.WEEKS);
		return new BudgetItem(null, "foo", "bar", Money.dollars(10), Frequency.WEEKLY, twoWeeksAgo, Optional.empty(), true);
	}
	
	@Test
	public void cononicalTransactionsWithExpectedNegativeValueEnsureCorrectRemainingAmount() {
		BudgetItem entry = tenDollarsEachWeekForTwoWeeks();
		BudgetItemSnapshot snapshot = createSnapshotForTransactionAmounts(entry, List.of(5, -10, -30, -20));
		
		// Accumulated amount:    $10/week * 3 weeks     = $30
		//     3 weeks because of this week + plus 2
		// Transaction total:     $5 - $10 - $30 - $20   = -$55
		// Total:                 -$55 + $20             = -$35
		assertEquals(Money.dollars(-25), snapshot.getRemainingAmount());
	}
	
	@Test
	public void givenValidWeeklyBudgetItemWhenLessThanOneWeekEnsureOneMonthAccumulated() {
		BudgetItem item = new BudgetItem(null, "foo", "bar", Money.dollars(5), Frequency.WEEKLY, LocalDate.now().minus(1, ChronoUnit.DAYS), Optional.empty(), true);
		BudgetItemSnapshot snapshot = BudgetItemSnapshot.withoutTransactions(item);
		assertEquals(Money.dollars(5), snapshot.getAccumulatedAmount());
	}
	
	@Test
	public void givenValidMonthlyBudgetItemWhenLessThanOneMonthEnsureOneMonthAccumulated() {
		BudgetItem item = new BudgetItem(null, "foo", "bar", Money.dollars(5), Frequency.MONTHLY, LocalDate.now().minus(1, ChronoUnit.DAYS), Optional.empty(), true);
		BudgetItemSnapshot snapshot = BudgetItemSnapshot.withoutTransactions(item);
		assertEquals(Money.dollars(5), snapshot.getAccumulatedAmount());
	}
}
