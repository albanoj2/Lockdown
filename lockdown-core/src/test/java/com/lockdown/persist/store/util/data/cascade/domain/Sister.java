package com.lockdown.persist.store.util.data.cascade.domain;

import java.util.List;

import com.lockdown.domain.DomainObject;

public class Sister extends DomainObject {

	private final double someFemaleKey;
	private final String name;
	private final List<Grandchild> children;
	
	public Sister(String id, double someFemaleKey, String name, List<Grandchild> children) {
		super(id);
		this.someFemaleKey = someFemaleKey;
		this.name = name;
		this.children = children;
	}

	public double getSomeFemaleKey() {
		return someFemaleKey;
	}

	public String getName() {
		return name;
	}

	public List<Grandchild> getChildren() {
		return children;
	}
}
