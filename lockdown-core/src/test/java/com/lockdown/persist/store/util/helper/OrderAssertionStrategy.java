package com.lockdown.persist.store.util.helper;

import java.util.List;

import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;

public interface OrderAssertionStrategy {
	public void assertOrder(List<MockDomainObject> objects);
}
