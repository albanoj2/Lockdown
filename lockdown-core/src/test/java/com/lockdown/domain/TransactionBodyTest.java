package com.lockdown.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class TransactionBodyTest {

	@Test
	public void compareIdentialBodiesEnsureBodiesAreIdentical() {
		Money commonAmount = Money.dollars(2);
		String commonName = "foo";
		String commonDescription = "bar";
		boolean commonIsPending = true;
		TransactionBody body1 = createBody(commonAmount, commonName, commonDescription, commonIsPending);
		TransactionBody body2 = createBody(commonAmount, commonName, commonDescription, commonIsPending);
		
		assertTrue(body1.equals(body2));
		assertTrue(body2.equals(body1));
		assertEquals(body1.hashCode(), body2.hashCode());
	}
	
	private static TransactionBody createBody(Money amount, String name, String description, boolean isPending) {
		return new TransactionBody(LocalDate.now(), amount, name, description, isPending);
	}
	
	@Test
	public void compareBodiesWithDifferentDatesEnsureBodiesAreDifferent() {
		Money commonAmount = Money.dollars(2);
		String commonName = "foo";
		String commonDescription = "bar";
		boolean commonIsPending = true;
		TransactionBody body1 = new TransactionBody(LocalDate.of(2018, 2, 15), commonAmount, commonName, commonDescription, commonIsPending);
		TransactionBody body2 = new TransactionBody(LocalDate.of(2017, 1, 30), commonAmount, commonName, commonDescription, commonIsPending);
		
		assertBodiesAreDifferent(body1, body2);
	}
	
	@Test
	public void compareBodiesWithDifferentAmountsEnsureBodiesAreDifferent() {
		String commonName = "foo";
		String commonDescription = "bar";
		boolean commonIsPending = true;
		TransactionBody body1 = createBody(Money.dollars(5), commonName, commonDescription, commonIsPending);
		TransactionBody body2 = createBody(Money.dollars(7), commonName, commonDescription, commonIsPending);
		
		assertBodiesAreDifferent(body1, body2);
	}
	
	private static void assertBodiesAreDifferent(TransactionBody body1, TransactionBody body2) {
		assertFalse(body1.equals(body2));
		assertFalse(body2.equals(body1));
		assertNotEquals(body1.hashCode(), body2.hashCode());
	}
	
	@Test
	public void compareBodiesWithDifferentNamesEnsureBodiesAreDifferent() {
		Money commonAmount = Money.dollars(2);
		String commonDescription = "bar";
		boolean commonIsPending = true;
		TransactionBody body1 = createBody(commonAmount, "foo1", commonDescription, commonIsPending);
		TransactionBody body2 = createBody(commonAmount, "foo2", commonDescription, commonIsPending);
		
		assertBodiesAreDifferent(body1, body2);
	}
	
	@Test
	public void compareBodiesWithDifferentDescriptionsEnsureBodiesAreDifferent() {
		Money commonAmount = Money.dollars(2);
		String commonName = "foo";
		boolean commonIsPending = true;
		TransactionBody body1 = createBody(commonAmount, commonName, "bar1", commonIsPending);
		TransactionBody body2 = createBody(commonAmount, commonName, "bar2", commonIsPending);
		
		assertBodiesAreDifferent(body1, body2);
	}
	
	@Test
	public void compareBodiesWithDifferentIsPendingStatesEnsureBodiesAreDifferent() {
		Money commonAmount = Money.dollars(2);
		String commonName = "foo";
		String commonDescription = "bar";
		TransactionBody body1 = createBody(commonAmount, commonName, commonDescription, true);
		TransactionBody body2 = createBody(commonAmount, commonName, commonDescription, false);
		
		assertBodiesAreDifferent(body1, body2);
	}
	
	@Test
	public void givenTransactionsBodyWithAllNullInformationEnsureHashCodeIsNotBlank() {
		TransactionBody body = new TransactionBody(null, null, null, null, false);
		assertHashCodeIsNotBlank(body);
	}
	
	private static void assertHashCodeIsNotBlank(TransactionBody body) {
		assertNotNull(body.hashCode());
		assertNotEquals(0, body.hashCode());
	}
	
	@Test
	public void compareToNonTransactionBodyEnsureNotEquals() {
		TransactionBody body = createBody(Money.dollars(2), "foo", "bar", true);
		assertFalse(body.equals(new Object()));
	}
}
