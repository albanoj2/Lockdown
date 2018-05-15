package com.lockdown.service.plaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lockdown.domain.account.InstitutionCredentials;
import com.lockdown.rest.InstitutionCredentialsRepository;
import com.lockdown.service.DataSynchronizer;

@Component
public class PlaidDataSynchronizer implements DataSynchronizer {
	
	@Autowired
	private InstitutionCredentialsRepository credentialsRepository;

	@Override
	public void synchornize() {
		for (InstitutionCredentials credentials: credentialsRepository.findAll()) {
			System.out.println("Item ID: " + credentials.getItemId() + ", token: " + credentials.getAccessToken());
		}
	}
}
