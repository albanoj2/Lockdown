package com.lockdown.persist.store.util.data.cascade.domain;

import java.util.ArrayList;
import java.util.List;

public class Child extends MockDomainObject {

	private final double someKey;
	private final int anotherKey;
	private final String name;
	private final List<Grandchild> children;
	
	public Child(String id, double someKey, int anotherKey, String name, List<Grandchild> children) {
		super(id);
		this.someKey = someKey;
		this.anotherKey = anotherKey;
		this.name = name;
		this.children = children;
	}

	public double getSomeKey() {
		return someKey;
	}

	public int getAnotherKey() {
		return anotherKey;
	}

	public String getName() {
		return name;
	}

	public List<Grandchild> getChildren() {
		return children;
	}
	
	@Override
	public Child copy() {
		return new Child(getId(), someKey, anotherKey, name, new ArrayList<>(children));
	}
}
