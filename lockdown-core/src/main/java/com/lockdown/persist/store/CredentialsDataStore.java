package com.lockdown.persist.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Credentials;
import com.lockdown.persist.dto.CredentialsDto;
import com.lockdown.persist.repository.CredentialsRepository;
import com.lockdown.persist.repository.LockdownRepository;

@Service
public class CredentialsDataStore extends AbstractDataStore<Credentials, CredentialsDto> {
	
	@Autowired
	private CredentialsRepository repository;

	@Override
	protected CredentialsDto fromDomainObject(Credentials domainObject) {
		return new CredentialsDto(domainObject);
	}

	@Override
	protected Credentials toDomainObject(CredentialsDto dto) {
		return dto.toCredentials();
	}

	@Override
	protected LockdownRepository<CredentialsDto> getRepository() {
		return repository;
	}

}
