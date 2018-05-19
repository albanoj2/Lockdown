package com.lockdown.persist.store;

import org.springframework.stereotype.Service;

import com.lockdown.domain.SynchronizationLogEntry;
import com.lockdown.persist.dto.SynchronizationLogEntryDto;

@Service
@DataStoreFor(SynchronizationLogEntry.class)
public class SynchronizationLogEntryDataStore
		extends AbstractDataStore<SynchronizationLogEntry, SynchronizationLogEntryDto> {

	@Override
	protected SynchronizationLogEntryDto fromDomainObject(SynchronizationLogEntry domainObject) {
		return new SynchronizationLogEntryDto(domainObject);
	}

	@Override
	protected SynchronizationLogEntry toDomainObject(SynchronizationLogEntryDto dto) {
		return dto.toDomainObject();
	}

}
