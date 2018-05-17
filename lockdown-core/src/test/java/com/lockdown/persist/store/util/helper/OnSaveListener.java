package com.lockdown.persist.store.util.helper;

import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;

@FunctionalInterface
public interface OnSaveListener {
	public void onSave(MockDomainObject object);
}
