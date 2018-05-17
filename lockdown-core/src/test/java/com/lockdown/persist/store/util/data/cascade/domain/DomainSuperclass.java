package com.lockdown.persist.store.util.data.cascade.domain;

public class DomainSuperclass extends MockDomainObject {
	
	private final Parent parent;
	
	public DomainSuperclass(String id, Parent parent) {
		super(id);
		this.parent = parent;
	}

	public Parent getParent() {
		return parent;
	}

	@Override
	public MockDomainObject copy() {
		return new DomainSuperclass(getId(), parent);
	}
}
