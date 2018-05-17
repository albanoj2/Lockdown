package com.lockdown.persist.store.util.data.cascade.domain;

import com.lockdown.domain.DomainObject;

public abstract class MockDomainObject extends DomainObject {
	
	protected MockDomainObject(String id) {
		super(id);
	}
	
	public abstract MockDomainObject copy();
}
