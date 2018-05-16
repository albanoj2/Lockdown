package com.lockdown.service;

import com.lockdown.domain.account.Institution;
import com.lockdown.service.context.InstitutionSynchronizerContext;

public interface InstitutionSynchronizer {
	public void synchronize(Institution institution, InstitutionSynchronizerContext context);
}
