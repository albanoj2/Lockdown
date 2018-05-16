package com.lockdown.service;

import com.lockdown.domain.account.InstitutionCredentials;
import com.lockdown.service.context.CredentialsSynchrnozierContext;

public interface CredentialsSynchronizer {
	public void synchronize(InstitutionCredentials credentials, CredentialsSynchrnozierContext context);
}
