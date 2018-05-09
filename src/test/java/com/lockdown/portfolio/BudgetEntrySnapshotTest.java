package com.lockdown.portfolio;

import static com.lockdown.money.DollarAmount.dollars;
import static com.lockdown.money.DollarAmount.zero;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.lockdown.account.BudgetEntryMapping;
import com.lockdown.account.SingleBudgetEntryMapping;
import com.lockdown.account.Transaction;
import com.lockdown.budget.BudgetEntry;
import com.lockdown.budget.FrequencyUnits;
import com.lockdown.money.DollarAmount;

public class BudgetEntrySnapshotTest {

	@Test
	public void noTransactionsEnsureExpensedAmountIsZero() {
		assertEquals(DollarAmount.zero(), emptySnapshot().getExpensedAmount());
	}

	private static BudgetEntrySnapshot emptySnapshot() {
		return new BudgetEntrySnapshot(BudgetEntry.none(), new ArrayList<>());
	}
	
	@Test
	public void noTransactionsEnsureDepositedAmountIsZero() {
		assertEquals(DollarAmount.zero(), emptySnapshot().getDepositedAmount());
	}
	
	@Test
	public void singleNegativeTransactionEnsureCorrectExpensedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(-3));
		assertEquals(DollarAmount.dollars(3), snapshot.getExpensedAmount());
	}
	
	private static BudgetEntrySnapshot createSnapshotForTransactionAmounts(List<Integer> amounts) {
		BudgetEntry entry = createBlankEntry();
		BudgetEntryMapping mapping = createMappingFor(entry);
		return createSnapshotForTransactionAmounts(entry, mapping, amounts);
	}
	
	private static BudgetEntrySnapshot createSnapshotForTransactionAmounts(BudgetEntry entry, BudgetEntryMapping mapping, List<Integer> amounts) {
		List<Transaction> transactions = amounts.stream()
			.map(amount -> Transaction.now(DollarAmount.dollars(amount), mapping))
			.collect(Collectors.toList());
		
		return new BudgetEntrySnapshot(entry, transactions);
	}
	
	private static BudgetEntry createBlankEntry() {
		return createEntry(0);
	}
	
	private static BudgetEntry createEntry(int dollars) {
		return BudgetEntry.startingNow(0, DollarAmount.dollars(dollars), FrequencyUnits.WEEKLY);
	}
	
	private static BudgetEntryMapping createMappingFor(BudgetEntry entry) {
		return new SingleBudgetEntryMapping(entry);
	}
	
	@Test
	public void twoNegativeTransactionsEnsureCorrectExpensedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(-3, -7));
		assertEquals(DollarAmount.dollars(10), snapshot.getExpensedAmount());
	}
	
	@Test
	public void oneNegativeOnePositiveTransactionEnsureCorrectExpensedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3, -7));
		assertEquals(DollarAmount.dollars(7), snapshot.getExpensedAmount());
	}
	
	@Test
	public void twoNegativeTransactionsWithFixedMappingAmountEnsureCorrectExpensedAmount() {
		BudgetEntry entry = createBlankEntry();
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(entry, (t, e) -> DollarAmount.zero(), List.of(-3, -7));
		assertEquals(DollarAmount.zero(), snapshot.getExpensedAmount());
	}

	@Test
	public void twoNegativeTransactionsWithOneNotMappedToBudgetEntryEnsureCorrectExpensedAmount() {
		BudgetEntry entry = createBlankEntry();
		Transaction mappedTransaction = createMappedTransaction(dollars(-3), entry);
		Transaction unmappedTransaction = createUnmappedTransaction(dollars(-3));
		BudgetEntrySnapshot snapshot = new BudgetEntrySnapshot(entry, List.of(mappedTransaction, unmappedTransaction));
		assertEquals(dollars(3), snapshot.getExpensedAmount());
	}
	
	private static Transaction createMappedTransaction(DollarAmount amount, BudgetEntry entry) {
		return Transaction.now(amount, createMappingFor(entry));
	}
	
	private static Transaction createUnmappedTransaction(DollarAmount amount) {
		return Transaction.now(amount, (t, e) -> zero());
	}
	

	
	@Test
	public void singlePositiveTransactionEnsureCorrectDepositedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3));
		assertEquals(DollarAmount.dollars(3), snapshot.getDepositedAmount());
	}
	
	@Test
	public void twoPositiveTransactionsEnsureCorrectDepositedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3, 7));
		assertEquals(DollarAmount.dollars(10), snapshot.getDepositedAmount());
	}
	
	@Test
	public void oneNegativeOnePositiveTransactionEnsureCorrectDepositedAmount() {
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(List.of(3, -7));
		assertEquals(DollarAmount.dollars(3), snapshot.getDepositedAmount());
	}
	
	@Test
	public void twoPositiveTransactionsWithFixedMappingAmountEnsureCorrectDepositedAmount() {
		BudgetEntry entry = createBlankEntry();
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(entry, (t, e) -> DollarAmount.zero(), List.of(3, 7));
		assertEquals(DollarAmount.zero(), snapshot.getDepositedAmount());
	}

	@Test
	public void twoPositiveTransactionsWithOneNotMappedToBudgetEntryEnsureCorrectDepositedAmount() {
		BudgetEntry entry = createBlankEntry();
		Transaction mappedTransaction = createMappedTransaction(dollars(3), entry);
		Transaction unmappedTransaction = createUnmappedTransaction(dollars(3));
		BudgetEntrySnapshot snapshot = new BudgetEntrySnapshot(entry, List.of(mappedTransaction, unmappedTransaction));
		assertEquals(dollars(3), snapshot.getDepositedAmount());
	}
	
	@Test
	public void cononicalTransactionsEnsureCorrectRemainingAmount() {
		BudgetEntry entry = new BudgetEntry(DollarAmount.dollars(10), LocalDate.EPOCH, LocalDate.EPOCH.plusWeeks(2), FrequencyUnits.WEEKLY);
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(entry, new SingleBudgetEntryMapping(entry), List.of(5, -10, 30, -20));
		
		// Accumulated amount:    $10/week * 2 weeks     = $20
		// Transaction total:     $5 - $10 + $30 - $20   = $5
		// Total:                 $5 + $20               = $25
		assertEquals(DollarAmount.dollars(25), snapshot.getRemainingAmount());
	}
	
	@Test
	public void cononicalTransactionsWithExpectedNegativeValueEnsureCorrectRemainingAmount() {
		BudgetEntry entry = new BudgetEntry(DollarAmount.dollars(10), LocalDate.EPOCH, LocalDate.EPOCH.plusWeeks(2), FrequencyUnits.WEEKLY);
		BudgetEntrySnapshot snapshot = createSnapshotForTransactionAmounts(entry, new SingleBudgetEntryMapping(entry), List.of(5, -10, -30, -20));
		
		// Accumulated amount:    $10/week * 2 weeks     = $20
		// Transaction total:     $5 - $10 - $30 - $20   = -$55
		// Total:                 -$55 + $20               = -$35
		assertEquals(DollarAmount.dollars(-35), snapshot.getRemainingAmount());
	}
}
