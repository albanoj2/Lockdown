package com.lockdown.persist.store;

import org.springframework.stereotype.Service;

import com.lockdown.domain.Credentials;
import com.lockdown.persist.dto.CredentialsDto;

@Service
@DataStoreFor(Credentials.class)
public class CredentialsDataStore extends AbstractDataStore<Credentials, CredentialsDto> {

	@Override
	protected CredentialsDto fromDomainObject(Credentials domainObject) {
		return new CredentialsDto(domainObject);
	}

	@Override
	protected Credentials toDomainObject(CredentialsDto dto) {
		return dto.toCredentials();
	}
}
