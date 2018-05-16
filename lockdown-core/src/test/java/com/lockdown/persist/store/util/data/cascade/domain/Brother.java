package com.lockdown.persist.store.util.data.cascade.domain;

import java.util.List;

import com.lockdown.domain.DomainObject;

public class Brother extends DomainObject {

	private final long someMaleKey;
	private final String name;
	private final List<Grandchild> children;
	
	public Brother(String id, long someMaleKey, String name, List<Grandchild> children) {
		super(id);
		this.someMaleKey = someMaleKey;
		this.name = name;
		this.children = children;
	}

	public long getSomeMaleKey() {
		return someMaleKey;
	}

	public String getName() {
		return name;
	}

	public List<Grandchild> getChildren() {
		return children;
	}
}
