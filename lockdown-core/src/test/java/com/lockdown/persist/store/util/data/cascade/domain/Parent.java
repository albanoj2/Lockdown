package com.lockdown.persist.store.util.data.cascade.domain;

import java.util.ArrayList;
import java.util.List;

public class Parent extends MockDomainObject {

	private final String name;
	private final List<Child> firstSubtree;
	private final List<Child> secondSubtree;
	
	public Parent(String id, String name, List<Child> firstSubtree, List<Child> secondSubtree) {
		super(id);
		this.name = name;
		this.firstSubtree = firstSubtree;
		this.secondSubtree = secondSubtree;
	}

	public String getName() {
		return name;
	}

	public List<Child> getFirstSubtree() {
		return firstSubtree;
	}

	public List<Child> getSecondSubtree() {
		return secondSubtree;
	}
	
	@Override
	public Parent copy() {
		return new Parent(getId(), name, new ArrayList<>(firstSubtree), new ArrayList<>(secondSubtree));
	}
}
