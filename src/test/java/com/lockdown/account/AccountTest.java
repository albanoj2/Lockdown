package com.lockdown.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lockdown.money.DollarAmount;

public class AccountTest {

	private Account account;
	
	@BeforeEach
	public void setUp() {
		this.account = new Account();
	}
	
	@Test
	public void noTransactionsEnsureZeroBalance() {
		assertAccountBalanceIsZero();
	}
	
	private void assertAccountBalanceIsZero() {
		assertAccountBalanceIs(DollarAmount.zero());
	}
	
	private void assertAccountBalanceIs(DollarAmount amount) {
		assertEquals(account.getBalance(), amount);
	}
	
	@Test
	public void oneTransactionWithZeroValueEnsureZeroBalance() {
		Transaction transaction = Transaction.now(DollarAmount.zero());
		account.addTransaction(transaction);
		assertAccountBalanceIsZero();
	}
	
	@Test
	public void oneTransactionWithPositiveValueEnsureZeroBalance() {
		addTransactionWithAmount(1);
		assertAccountBalanceIs(DollarAmount.cents(1));
	}
	
	private void addTransactionWithAmount(long amount) {
		Transaction transaction = Transaction.now(DollarAmount.cents(amount));
		account.addTransaction(transaction);
	}
	
	@Test
	public void twoTransactionsWithPositiveValuesEnsureZeroBalance() {
		addTransactionWithAmount(1);
		addTransactionWithAmount(20);
		assertAccountBalanceIs(DollarAmount.cents(21));
	}
	
	@Test
	public void twoTransactionsWithNegativeValuesEnsureZeroBalance() {
		addTransactionWithAmount(-1);
		addTransactionWithAmount(-20);
		assertAccountBalanceIs(DollarAmount.cents(-21));
	}
	
	@Test
	public void twoTransactionsWithOneNegativeAndOnePositiveValueEnsureZeroBalance() {
		addTransactionWithAmount(-1);
		addTransactionWithAmount(20);
		assertAccountBalanceIs(DollarAmount.cents(19));
	}
}
