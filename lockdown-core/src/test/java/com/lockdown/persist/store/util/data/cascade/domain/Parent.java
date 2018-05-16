package com.lockdown.persist.store.util.data.cascade.domain;

import java.util.List;

import com.lockdown.domain.DomainObject;

public class Parent extends DomainObject {

	private final String name;
	private final List<Brother> brothers;
	private final List<Sister> sisters;
	
	public Parent(String id, String name, List<Brother> brothers, List<Sister> sisters) {
		super(id);
		this.name = name;
		this.brothers = brothers;
		this.sisters = sisters;
	}

	public String getName() {
		return name;
	}

	public List<Brother> getBrothers() {
		return brothers;
	}

	public List<Sister> getSisters() {
		return sisters;
	}
}
