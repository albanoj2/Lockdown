package com.lockdown.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BudgetItemMappingTest {

	private BudgetItemMapping mapping;
	
	@Before
	public void setUp() {
		mapping = new BudgetItemMapping();
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
		
		BudgetItem item = budgetItemWithId("item");
		BudgetItem anotherItem = budgetItemWithId("anotherItem");
		mapping.addMapping(item, expectedAmount);
		
		assertEquals(expectedAmount, mapping.amountFor(transaction, item));
		assertEquals(Money.zero(), mapping.amountFor(transaction, anotherItem));
	}
	
	@Test
	public void givenTwoMappingsWhenGetAmountForThenCorrectAmounts() {
		Transaction transaction = new Transaction();
		Money expectedAmount1 = Money.dollars(7);
		Money expectedAmount2 = Money.dollars(25);
		
		BudgetItem item1 = budgetItemWithId("item1");
		BudgetItem item2 = budgetItemWithId("item2");
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
		mapping.addMapping(budgetItemWithId("foo"), Money.dollars(5));
		mapping.addMapping(budgetItemWithId("bar"), Money.dollars(7));
		assertEquals(Money.dollars(12), mapping.mappedAmount(transaction));
	}
	
	private static BudgetItem budgetItemWithId(String id) {
		BudgetItem item = BudgetItem.blank();
		item.setId(id);
		return item;
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
		mapping.addMapping(budgetItemWithId("foo"), Money.dollars(2));
		mapping.addMapping(budgetItemWithId("bar"), Money.dollars(5));
		assertTrue(mapping.isValidFor(transaction));
	}
	
	@Test
	public void givenNoMappingsAddMappingEnsureMappingIsPresent() {
		BudgetItem budgetItem = BudgetItem.blank();
		Money amount = Money.dollars(5);
		mapping.addMapping(budgetItem, amount);
		assertEquals(1, mapping.getMappingsCount());
		assertEquals(amount, mapping.getMappings().get(budgetItem));
	}
	
	@Test
	public void givenNoMappingsAddMappingAndThenRemoveSameMappingEnsureMappingIsRemoved() {
		BudgetItem budgetItem = BudgetItem.blank();
		Money amount = Money.dollars(5);
		mapping.addMapping(budgetItem, amount);
		mapping.removeMapping(budgetItem);
		assertEquals(0, mapping.getMappingsCount());
	}
	
	@Test
	public void givenValidMappingWhenAmountForDifferentBudgetItemObjectWithSameIdAsBudgetItemKeyThenCorrectAmountFor() {
		
		String commonId = "foo";
		Money expectedAmount = Money.dollars(42);
		
		BudgetItem key = budgetItemWithId(commonId);
		BudgetItem desiredBudgetItem = budgetItemWithId(commonId);
		
		BudgetItemMapping mapping = new BudgetItemMapping();
		mapping.addMapping(key, expectedAmount);
		
		assertFalse(key == desiredBudgetItem);
		assertEquals(expectedAmount, mapping.amountFor(new Transaction(), desiredBudgetItem));
	}
	
	@Test
	public void givenTwoBudgetItemsWithSameIdThenBudgetItemsAreEqual() {
		BudgetItem item1 = budgetItemWithId("item1");
		BudgetItem item2 = budgetItemWithId("item1");
		assertEquals(item1, item2);
	}
	
	@Test
	public void givenTwoBudgetItemsWithDifferentIdsThenBudgetItemsAreNotEqual() {
		BudgetItem item1 = budgetItemWithId("foo");
		BudgetItem item2 = budgetItemWithId("bar");
		assertNotEquals(item1, item2);
	}

	@Test
	public void givenTwoBudgetItemsWhenFirstIsNullThenBudgetItemsAreNotEqual() {
		BudgetItem item1 = budgetItemWithId(null);
		BudgetItem item2 = budgetItemWithId("bar");
		assertNotEquals(item1, item2);
	}

	@Test
	public void givenTwoBudgetItemsWhenSecondIsNullThenBudgetItemsAreNotEqual() {
		BudgetItem item1 = budgetItemWithId("foo");
		BudgetItem item2 = budgetItemWithId(null);
		assertNotEquals(item1, item2);
	}

	@Test
	public void givenTwoBudgetItemsWhenBothAreNullThenBudgetItemsAreNotEqual() {
		BudgetItem item1 = budgetItemWithId(null);
		BudgetItem item2 = budgetItemWithId(null);
		assertNotEquals(item1, item2);
	}
	
	@Test
	public void givenTwoBudgetItemsWithSameIdThenBudgetItemsHaveSameHashCode() {
		BudgetItem item1 = budgetItemWithId("item1");
		BudgetItem item2 = budgetItemWithId("item1");
		assertEquals(item1.hashCode(), item2.hashCode());
	}
	
	@Test
	public void givenTwoBudgetItemsWithDifferentIdsThenBudgetItemsHaveDifferentHashCodes() {
		BudgetItem item1 = budgetItemWithId("foo");
		BudgetItem item2 = budgetItemWithId("bar");
		assertNotEquals(item1.hashCode(), item2.hashCode());
	}

	@Test
	public void givenTwoBudgetItemsWhenFirstIsNullThenBudgetItemsHaveDifferentHashCodes() {
		BudgetItem item1 = budgetItemWithId(null);
		BudgetItem item2 = budgetItemWithId("bar");
		assertNotEquals(item1.hashCode(), item2.hashCode());
	}

	@Test
	public void givenTwoBudgetItemsWhenSecondIsNullThenBudgetItemsHaveDifferentHashCodes() {
		BudgetItem item1 = budgetItemWithId("foo");
		BudgetItem item2 = budgetItemWithId(null);
		assertNotEquals(item1.hashCode(), item2.hashCode());
	}

	@Test
	public void givenTwoBudgetItemsWhenBothAreNullThenBudgetItemsHaveDifferentHashCodes() {
		BudgetItem item1 = budgetItemWithId(null);
		BudgetItem item2 = budgetItemWithId(null);
		assertNotEquals(item1.hashCode(), item2.hashCode());
	}
}
