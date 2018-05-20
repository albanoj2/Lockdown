package com.lockdown.persist.store.util.data.cascade.domain;

import com.lockdown.domain.Identifable;

public abstract class MockDomainObject extends Identifable {
	
	protected MockDomainObject(String id) {
		super(id);
	}
	
	public abstract MockDomainObject copy();
}
