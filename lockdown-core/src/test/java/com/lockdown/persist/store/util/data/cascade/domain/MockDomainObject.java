package com.lockdown.persist.store.util.data.cascade.domain;

import com.lockdown.domain.Identifiable;

public abstract class MockDomainObject extends Identifiable {
	
	protected MockDomainObject(String id) {
		super(id);
	}
	
	public abstract MockDomainObject copy();
}
