package com.lockdown.service;

import com.lockdown.domain.account.Credentials;
import com.lockdown.service.context.CredentialsSynchrnozierContext;

public interface CredentialsSynchronizer {
	public void synchronize(Credentials credentials, CredentialsSynchrnozierContext context);
}
