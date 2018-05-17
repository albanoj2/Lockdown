package com.lockdown.persist.store.util.data.cascade.domain;

public class DomainSubclass extends DomainSuperclass {

	private final Child child;
	
	public DomainSubclass(String id, Parent parent, Child child) {
		super(id, parent);
		this.child = child;
	}

	public Child getChild() {
		return child;
	}

	@Override
	public MockDomainObject copy() {
		return new DomainSubclass(getId(), getParent(), child);
	}
}
