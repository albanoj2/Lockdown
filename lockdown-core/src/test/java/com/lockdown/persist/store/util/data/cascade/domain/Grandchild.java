package com.lockdown.persist.store.util.data.cascade.domain;

import com.lockdown.domain.DomainObject;

public class Grandchild extends DomainObject {

	private final boolean someFlag;

	public Grandchild(String id, boolean someFlag) {
		super(id);
		this.someFlag = someFlag;
	}

	public boolean isSomeFlag() {
		return someFlag;
	}
}
