package com.lockdown.domain;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

public class SynchronizationLogEntryTest {

	@Test
	public void givenDefaultSynchronizationLogEntryEnsureDefaultValues() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		assertEquals(entry.getStart(), entry.getStop());
		assertEquals(0, entry.getElapsedTime(ChronoUnit.MICROS));
		assertEquals(0, entry.getAccountsDiscovered());
		assertEquals(0, entry.getAccountsAdded());
		assertEquals(0, entry.getTransactionsDiscovered());
		assertEquals(0, entry.getTransactionsAdded());
		assertEquals(0, entry.getTransactionsUpdated());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryWhenStoppingEntryAfterStartEnsureCorrectElapsedTime() throws Exception {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.start();
		Thread.sleep(250);
		entry.stop();
		assertEqualsWithin(250, entry.getElapsedTime(ChronoUnit.MILLIS), 25);
	}
	
	private static void assertEqualsWithin(long expected, long actual, long epsilon) {
		assertTrue("Expected: " + expected + ", actual: " + actual, Math.abs(expected - actual) <= epsilon);
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryEnsureElapsedTimeStringIsCorrect() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		assertEquals("00:00.000", entry.formattedElapsedTime());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryWith1MillisecondEnsureElapsedTimeStringIsCorrect() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.setStop(entry.getStart().plus(1, ChronoUnit.MILLIS));
		assertEquals("00:00.001", entry.formattedElapsedTime());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryWith999MillisecondsEnsureElapsedTimeStringIsCorrect() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.setStop(entry.getStart().plus(999, ChronoUnit.MILLIS));
		assertEquals("00:00.999", entry.formattedElapsedTime());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryWith1000MillisecondsEnsureElapsedTimeStringIsCorrect() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.setStop(entry.getStart().plus(1000, ChronoUnit.MILLIS));
		assertEquals("00:01.000", entry.formattedElapsedTime());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryWith1SecondEnsureElapsedTimeStringIsCorrect() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.setStop(entry.getStart().plus(1, ChronoUnit.SECONDS));
		assertEquals("00:01.000", entry.formattedElapsedTime());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryWith59SecondsEnsureElapsedTimeStringIsCorrect() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.setStop(entry.getStart().plus(59, ChronoUnit.SECONDS));
		assertEquals("00:59.000", entry.formattedElapsedTime());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryWith60SecondsEnsureElapsedTimeStringIsCorrect() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.setStop(entry.getStart().plus(60, ChronoUnit.SECONDS));
		assertEquals("01:00.000", entry.formattedElapsedTime());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryWith1MinutesEnsureElapsedTimeStringIsCorrect() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.setStop(entry.getStart().plus(1, ChronoUnit.MINUTES));
		assertEquals("01:00.000", entry.formattedElapsedTime());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createSynchronizationLogEntryWithStopTimeBeforeStopTimeEnsureIllegalArgumentExceptionThrown() {
		LocalDateTime start = LocalDateTime.now();
		new SynchronizationLogEntry(null, start, start.minus(1, ChronoUnit.MILLIS), 0, 0, 0, 0, 0);
	}
	
	@Test
	public void createSynchronizationLogEntryWithEqualStartAndStopTimeEnsureIllegalArgumentExceptionNotThrown() {
		LocalDateTime start = LocalDateTime.now();
		new SynchronizationLogEntry(null, start, start, 0, 0, 0, 0, 0);
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryThenIncrementAccountsDiscoveredEnsureCorrectAccountDiscovered() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.incrementAccountsDiscoveredBy(1);
		assertEquals(1, entry.getAccountsDiscovered());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryThenIncrementAccountsAddedEnsureCorrectAccountsAdded() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.incrementAccountsAdded();
		assertEquals(1, entry.getAccountsAdded());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryThenIncrementTransactionsDiscoveredEnsureCorrectTransactionsDiscovered() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.incrementTransactionsDiscoveredBy(1);
		assertEquals(1, entry.getTransactionsDiscovered());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryThenIncrementTransactionsAddedEnsureCorrectTransactionsAdded() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.incrementTransactionsAdded();
		assertEquals(1, entry.getTransactionsAdded());
	}
	
	@Test
	public void givenDefaultSynchronizationLogEntryThenIncrementTransactionsUpdatedEnsureCorrectTransactionsUpdated() {
		SynchronizationLogEntry entry = new SynchronizationLogEntry();
		entry.incrementTransactionsUpdated();
		assertEquals(1, entry.getTransactionsUpdated());
	}
}
