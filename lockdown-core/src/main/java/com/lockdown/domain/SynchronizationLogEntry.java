package com.lockdown.domain;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SynchronizationLogEntry extends Identifiable {

	private LocalDateTime start = LocalDateTime.now();
	private LocalDateTime stop = start;
	private int accountsDiscovered = 0;
	private int accountsAdded = 0;
	private int transactionsDiscovered = 0;
	private int transactionsAdded = 0;
	private int transactionsUpdated = 0;
	
	public SynchronizationLogEntry(String id, LocalDateTime start, LocalDateTime stop, int accountDiscovered, int accountAdded,
			int transactionsDiscovered, int transactionsAdded, int transactionsUpdated) {
		super(id);
		
		if (stop.isBefore(start)) {
			throw new IllegalArgumentException("Stop time cannot be before start time");
		}
		
		this.start = start;
		this.stop = stop;
		this.accountsDiscovered = accountDiscovered;
		this.accountsAdded = accountAdded;
		this.transactionsDiscovered = transactionsDiscovered;
		this.transactionsAdded = transactionsAdded;
		this.transactionsUpdated = transactionsUpdated;
	}
	
	public SynchronizationLogEntry() {}

	public LocalDateTime getStart() {
		return start;
	}
	
	public void start() {
		start = LocalDateTime.now();
	}
	
	void setStart(LocalDateTime start) {
		this.start = start;
	}
	
	public LocalDateTime getStop() {
		return stop;
	}
	
	void setStop(LocalDateTime stop) {
		this.stop = stop;
	}
	
	public void stop() {
		stop = LocalDateTime.now();
	}
	
	public long getElapsedTime(ChronoUnit units) {
		return units.between(start, stop);
	}
	
	public int getAccountsDiscovered() {
		return accountsDiscovered;
	}
	
	public void incrementAccountsDiscoveredBy(int amount) {
		accountsDiscovered += amount;
	}
	
	public int getAccountsAdded() {
		return accountsAdded;
	}
	
	public void incrementAccountsAdded() {
		accountsAdded++;
	}
	
	public int getTransactionsDiscovered() {
		return transactionsDiscovered;
	}
	
	public void incrementTransactionsDiscoveredBy(int amount) {
		transactionsDiscovered += amount;
	}
	
	public int getTransactionsAdded() {
		return transactionsAdded;
	}
	
	public void incrementTransactionsAdded() {
		transactionsAdded++;
	}
	
	public int getTransactionsUpdated() {
		return transactionsUpdated;
	}
	
	public void incrementTransactionsUpdated() {
		transactionsUpdated++;
	}

	@Override
	public String toString() {
		return "start: " + start + ", stop: " + stop + ", elapsed time: " + formattedElapsedTime() + ", accounts: [discovered: "
				+ accountsDiscovered + ", added: " + accountsAdded + "], transactions: [discovered: "
				+ transactionsDiscovered + ", added: " + transactionsAdded + ", updated: "
				+ transactionsUpdated + "]";
	}
	
	public String formattedElapsedTime() {
		return new SimpleDateFormat("mm:ss.SSS").format(getElapsedTime(ChronoUnit.MILLIS)).toString();
	}
}
