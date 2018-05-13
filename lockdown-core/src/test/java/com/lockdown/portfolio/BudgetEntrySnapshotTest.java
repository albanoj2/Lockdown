package com.lockdown.portfolio;

import static com.lockdown.money.Money.dollars;
import static org.junit.Assert.assertEquals;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.lockdown.account.BudgetEntryMapping;
import com.lockdown.account.BudgetedTransaction;
import com.lockdown.account.SingleBudgetEntryMapping;
import com.lockdown.account.Transactions;
import com.lockdown.budget.BudgetEntry;
import com.lockdown.money.Money;

public class BudgetEntrySnapshotTest {

	@Test
	public void noTransactionsEnsureExpensedAmountIsZero() {
		assertEquals(Money.zero(), emptySnapshot().getExpensedAmount());
	}

	private static BudgetEntrySnapshot emptySnapshot() {
		return new BudgetEntrySnapshot(BudgetEntry.blank(), new ArrayList<>());
	}
	
	@Test
	public void noTransactionsEnsureDepositedAmountIsZero() {
		assertEquals(Money.zero(), emptySnapshot().getDepositedAmount());
	}
	
	@Test
	public void singleNegativeTransactionEnsureCorrectExpensedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(-3));
		assertEquals(Money.dollars(3), snapshot.getExpensedAmount());
	}
	
	private static BudgetEntrySnapshot createSnapshotForTransactionAmounts(List<Integer> amounts) {
		BudgetEntry entry = createBlankEntry();
		BudgetEntryMapping mapping = createMappingFor(entry);
		return createSnapshotForTransactionAmounts(entry, mapping, amounts);
	}
	
	private static BudgetEntrySnapshot createSnapshotForTransactionAmounts(BudgetEntry entry, BudgetEntryMapping mapping, List<Integer> amounts) {
		List<BudgetedTransaction> budgetedTransactions = amounts.stream()
			.map(amount -> Transactions.budgetedForAmountWithMapping(Money.dollars(amount), mapping))
			.collect(Collectors.toList());
		
		return new BudgetEntrySnapshot(entry, budgetedTransactions);
	}
	
	private static BudgetEntry createBlankEntry() {
		return createEntry(0);
	}
	
	private static BudgetEntry createEntry(int dollars) {
		return BudgetEntry.builder()
			.amount(Money.dollars(dollars))
			.startingNow()
			.weekly()
			.build();
	}
	
	private static BudgetEntryMapping createMappingFor(BudgetEntry entry) {
		return new SingleBudgetEntryMapping(entry);
	}
	
	@Test
	public void twoNegativeTransactionsEnsureCorrectExpensedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(-3, -7));
		assertEquals(Money.dollars(10), snapshot.getExpensedAmount());
	}
	
	@Test
	public void oneNegativeOnePositiveTransactionEnsureCorrectExpensedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3, -7));
		assertEquals(Money.dollars(7), snapshot.getExpensedAmount());
	}
	
	@Test
	public void twoNegativeTransactionsWithFixedMappingAmountEnsureCorrectExpensedAmount() {
		BudgetEntry entry = createBlankEntry();
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(entry, (t, e) -> Money.zero(), List.of(-3, -7));
		assertEquals(Money.zero(), snapshot.getExpensedAmount());
	}

	@Test
	public void twoNegativeTransactionsWithOneNotMappedToBudgetEntryEnsureCorrectExpensedAmount() {
		BudgetEntry entry = createBlankEntry();
		BudgetedTransaction mappedTransaction = createMappedTransaction(dollars(-3), entry);
		BudgetedTransaction unmappedTransaction = createUnmappedTransaction(dollars(-3));
		BudgetEntrySnapshot snapshot = new BudgetEntrySnapshot(entry, List.of(mappedTransaction, unmappedTransaction));
		assertEquals(dollars(3), snapshot.getExpensedAmount());
	}
	
	private static BudgetedTransaction createMappedTransaction(Money amount, BudgetEntry entry) {
		return Transactions.budgetedForAmountWithMapping(amount, createMappingFor(entry));
	}
	
	private static BudgetedTransaction createUnmappedTransaction(Money amount) {
		return Transactions.budgetedForAmount(amount);
	}
	
	@Test
	public void singlePositiveTransactionEnsureCorrectDepositedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3));
		assertEquals(Money.dollars(3), snapshot.getDepositedAmount());
	}
	
	@Test
	public void twoPositiveTransactionsEnsureCorrectDepositedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3, 7));
		assertEquals(Money.dollars(10), snapshot.getDepositedAmount());
	}
	
	@Test
	public void oneNegativeOnePositiveTransactionEnsureCorrectDepositedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3, -7));
		assertEquals(Money.dollars(3), snapshot.getDepositedAmount());
	}
	
	@Test
	public void twoPositiveTransactionsWithFixedMappingAmountEnsureCorrectDepositedAmount() {
		BudgetEntry entry = createBlankEntry();
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(entry, (t, e) -> Money.zero(), List.of(3, 7));
		assertEquals(Money.zero(), snapshot.getDepositedAmount());
	}

	@Test
	public void twoPositiveTransactionsWithOneNotMappedToBudgetEntryEnsureCorrectDepositedAmount() {
		BudgetEntry entry = createBlankEntry();
		BudgetedTransaction mappedTransaction = createMappedTransaction(dollars(3), entry);
		BudgetedTransaction unmappedTransaction = createUnmappedTransaction(dollars(3));
		BudgetEntrySnapshot snapshot = new BudgetEntrySnapshot(entry, List.of(mappedTransaction, unmappedTransaction));
		assertEquals(dollars(3), snapshot.getDepositedAmount());
	}
	
	@Test
	public void cononicalTransactionsEnsureCorrectRemainingAmount() {
		BudgetEntry entry = tenDollarsEachWeekForTwoWeeks();
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(entry, new SingleBudgetEntryMapping(entry), List.of(5, -10, 30, -20));
		
		// Accumulated amount:    $10/week * 2 weeks     = $20
		// Transaction total:     $5 - $10 + $30 - $20   = $5
		// Total:                 $5 + $20               = $25
		assertEquals(Money.dollars(25), snapshot.getRemainingAmount());
	}
	
	public static BudgetEntry tenDollarsEachWeekForTwoWeeks() {
		return BudgetEntry.builder()
			.amount(dollars(10))
			.life(Period.ofWeeks(2))
			.weekly()
			.build();
	}
	
	@Test
	public void cononicalTransactionsWithExpectedNegativeValueEnsureCorrectRemainingAmount() {
		BudgetEntry entry = tenDollarsEachWeekForTwoWeeks();
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(entry, new SingleBudgetEntryMapping(entry), List.of(5, -10, -30, -20));
		
		// Accumulated amount:    $10/week * 2 weeks     = $20
		// Transaction total:     $5 - $10 - $30 - $20   = -$55
		// Total:                 -$55 + $20               = -$35
		assertEquals(Money.dollars(-35), snapshot.getRemainingAmount());
	}
}
