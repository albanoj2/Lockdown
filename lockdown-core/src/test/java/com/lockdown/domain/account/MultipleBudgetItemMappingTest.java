package com.lockdown.domain.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.lockdown.domain.budget.BudgetItem;
import com.lockdown.domain.money.Money;

public class MultipleBudgetItemMappingTest {

	private MultipleBudgetItemMapping mapping;
	
	@Before
	public void setUp() {
		mapping = new MultipleBudgetItemMapping(null, new HashMap<>());
	}
	
	@Test(expected = NullPointerException.class)
	public void givenValidMappingWhenAddingNullMappingBudgetItemThenThrowNullPointerException() {
		mapping.addMapping(null, Money.dollars(5));
	}
	
	@Test(expected = NullPointerException.class)
	public void givenValidMappingWhenAddingNullMappingAmountThenThrowNullPointerException() {
		mapping.addMapping(new BudgetItem(), null);
	}
	
	@Test
	public void givenOneMappingsWhenGetAmountForThenCorrectAmounts() {
		Transaction transaction = new Transaction();
		Money expectedAmount = Money.dollars(5);
		
		BudgetItem item = new BudgetItem();
		BudgetItem anotherItem = new BudgetItem();
		mapping.addMapping(item, expectedAmount);
		
		assertEquals(expectedAmount, mapping.amountFor(transaction, item));
		assertEquals(Money.zero(), mapping.amountFor(transaction, anotherItem));
	}
	
	@Test
	public void givenTwoMappingsWhenGetAmountForThenCorrectAmounts() {
		Transaction transaction = new Transaction();
		Money expectedAmount1 = Money.dollars(7);
		Money expectedAmount2 = Money.dollars(25);
		
		BudgetItem item1 = new BudgetItem();
		BudgetItem item2 = new BudgetItem();
		BudgetItem anotherItem = new BudgetItem();
		mapping.addMapping(item1, expectedAmount1);
		mapping.addMapping(item2, expectedAmount2);
		
		assertEquals(expectedAmount1, mapping.amountFor(transaction, item1));
		assertEquals(expectedAmount2, mapping.amountFor(transaction, item2));
		assertEquals(Money.zero(), mapping.amountFor(transaction, anotherItem));
	}
	
	@Test
	public void givenOneMappingsOfZeroDollarsWhenGetAmountForThenCorrectAmounts() {
		Transaction transaction = new Transaction();
		Money expectedAmount = Money.zero();
		
		BudgetItem item = new BudgetItem();
		mapping.addMapping(item, expectedAmount);
		
		assertEquals(expectedAmount, mapping.amountFor(transaction, item));
	}
	
	@Test
	public void givenNoMappingsWhenGetMappedAmountForNonZeroTransactionThenCorrectMappedAmount() {
		Transaction transaction = Transactions.budgetedForAmount(Money.dollars(7));
		assertEquals(Money.zero(), mapping.mappedAmount(transaction));
	}
	
	@Test
	public void givenOneMappingWhenGetMappedAmountForNonZeroTransactionThenCorrectMappedAmount() {
		Transaction transaction = Transactions.budgetedForAmount(Money.dollars(7));
		mapping.addMapping(new BudgetItem(), Money.dollars(5));
		assertEquals(Money.dollars(5), mapping.mappedAmount(transaction));
	}
	
	@Test
	public void givenTwoMappingsWhenGetMappedAmountForNonZeroTransactionThenCorrectMappedAmount() {
		Transaction transaction = Transactions.budgetedForAmount(Money.dollars(7));
		mapping.addMapping(new BudgetItem(), Money.dollars(5));
		mapping.addMapping(new BudgetItem(), Money.dollars(7));
		assertEquals(Money.dollars(12), mapping.mappedAmount(transaction));
	}
	
	@Test
	public void givenNoMappingsWhenIsValidForNonZeroTransactionThenIsInvalid() {
		Transaction transaction = Transactions.budgetedForAmount(Money.dollars(7));
		assertFalse(mapping.isValidFor(transaction));
	}
	
	@Test
	public void givenOneMappingMatchingTransactionAmountWhenIsValidForNonZeroTransactionThenIsValid() {
		Transaction transaction = Transactions.budgetedForAmount(Money.dollars(7));
		mapping.addMapping(new BudgetItem(), transaction.getAmount());
		assertTrue(mapping.isValidFor(transaction));
	}
	
	@Test
	public void givenTwoMappingsWithTotalAmountsMatchingTransactionAmountWhenIsValidForNonZeroTransactionThenIsValid() {
		Transaction transaction = Transactions.budgetedForAmount(Money.dollars(7));
		mapping.addMapping(new BudgetItem(), Money.dollars(2));
		mapping.addMapping(new BudgetItem(), Money.dollars(5));
		assertTrue(mapping.isValidFor(transaction));
	}
}
