package com.lockdown.service;

public interface SynchronizerFactory {
	public CredentialsSynchronizer createCredentialsSynchronizer();
	public InstitutionSynchronizer createInstitutionSynchronizer();
	public AccountSynchronizer createAccountSynchronizer();
}
