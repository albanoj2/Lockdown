package com.lockdown.persist.store.util.data.cascade.domain;

public class Grandchild extends MockDomainObject {

	private final boolean someFlag;

	public Grandchild(String id, boolean someFlag) {
		super(id);
		this.someFlag = someFlag;
	}

	public boolean isSomeFlag() {
		return someFlag;
	}
	
	@Override
	public Grandchild copy() {
		return new Grandchild(getId(), someFlag);
	}
}
