package com.lockdown.persist.dto;

import java.time.LocalDateTime;

import com.lockdown.domain.SynchronizationLogEntry;

public class SynchronizationLogEntryDto extends Dto {

	private final LocalDateTime start;
	private final LocalDateTime stop;
	private final int accDis;
	private final int accAdd;
	private final int tranDis;
	private final int tranAdd;
	private final int tranUpd;
	
	public SynchronizationLogEntryDto(SynchronizationLogEntry entry) {
		super(entry.getId());
		this.start = entry.getStart();
		this.stop = entry.getStop();
		this.accDis = entry.getAccountsDiscovered();
		this.accAdd = entry.getAccountsAdded();
		this.tranDis = entry.getTransactionsDiscovered();
		this.tranAdd = entry.getTransactionsAdded();
		this.tranUpd = entry.getTransactionsUpdated();
	}
	
	public SynchronizationLogEntryDto() {
		this.start = null;
		this.stop = null;
		this.accDis = 0;
		this.accAdd = 0;
		this.tranDis = 0;
		this.tranAdd = 0;
		this.tranUpd = 0;
	}
	
	public SynchronizationLogEntry toDomainObject() {
		return new SynchronizationLogEntry(
			getId(),
			start,
			stop,
			accDis,
			accAdd,
			tranDis,
			tranAdd,
			tranUpd
		);
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getStop() {
		return stop;
	}

	public int getAccDis() {
		return accDis;
	}

	public int getAccAdd() {
		return accAdd;
	}

	public int getTranDisc() {
		return tranDis;
	}

	public int getTranAdd() {
		return tranAdd;
	}

	public int getTranUpd() {
		return tranUpd;
	}
}
