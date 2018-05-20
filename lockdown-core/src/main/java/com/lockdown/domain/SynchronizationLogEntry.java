package com.lockdown.domain;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SynchronizationLogEntry extends Identifable {

	private LocalDateTime start;
	private LocalDateTime stop;
	private int accountsDiscovered = 0;
	private int accountsAdded = 0;
	private int transactionsDiscovered = 0;
	private int transactionsAdded = 0;
	private int transactionsUpdated = 0;
	
	public SynchronizationLogEntry(String id, LocalDateTime start, LocalDateTime stop, int accountDiscovered, int accountAdded,
			int transactionsDiscovered, int transactionsAdded, int transactionsUpdated) {
		super(id);
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
	
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	
	public void start() {
		start = LocalDateTime.now();
	}
	
	public LocalDateTime getStop() {
		return stop;
	}
	
	public void setStop(LocalDateTime stop) {
		this.stop = stop;
	}
	
	public void stop() {
		stop = LocalDateTime.now();
	}
	
	public long getElapsedTime(ChronoUnit units) {
		
		if (start == null || stop == null) {
			return 0;
		}
		else {
			return units.between(start, stop);
		}
	}
	
	public int getAccountsDiscovered() {
		return accountsDiscovered;
	}
	
	public void setAccountsDiscovered(int accountDiscovered) {
		this.accountsDiscovered = accountDiscovered;
	}
	
	public void incrementAccountsDiscoveredBy(int amount) {
		accountsDiscovered += amount;
	}
	
	public int getAccountsAdded() {
		return accountsAdded;
	}
	
	public void setAccountsAdded(int accountAdded) {
		this.accountsAdded = accountAdded;
	}
	
	public void incrementAccountsAdded() {
		accountsAdded++;
	}
	
	public int getTransactionsDiscovered() {
		return transactionsDiscovered;
	}
	
	public void setTransactionsDiscovered(int transactionsDiscovered) {
		this.transactionsDiscovered = transactionsDiscovered;
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
	
	public void setTransactionsAdded(int transactionsAdded) {
		this.transactionsAdded = transactionsAdded;
	}
	
	public int getTransactionsUpdated() {
		return transactionsUpdated;
	}
	
	public void incrementTransactionsUpdated() {
		transactionsUpdated++;
	}
	
	public void setTransactionsUpdated(int transactionsUpdated) {
		this.transactionsUpdated = transactionsUpdated;
	}

	@Override
	public String toString() {
		return "start: " + start + ", stop: " + stop + ", elapsed time: " + formattedElapsedTime() + ", accounts: [discovered: "
				+ accountsDiscovered + ", added: " + accountsAdded + "], transactions: [discovered: "
				+ transactionsDiscovered + ", added: " + transactionsAdded + ", updated: "
				+ transactionsUpdated + "]";
	}
	
	private String formattedElapsedTime() {
		return new SimpleDateFormat("mm:ss.SSS").format(getElapsedTime(ChronoUnit.MILLIS)).toString();
	}
}
