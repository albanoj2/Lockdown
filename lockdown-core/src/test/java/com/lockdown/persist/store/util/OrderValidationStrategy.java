package com.lockdown.persist.store.util;

import java.util.List;

import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;

public interface OrderValidationStrategy {
	public void validateOrder(List<MockDomainObject> objects);
}
