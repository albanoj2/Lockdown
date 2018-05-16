package com.lockdown.persist.store.util.data.cascade.domain;

import java.util.List;

import com.lockdown.domain.DomainObject;

public class Parent extends DomainObject {

	private final String name;
	private final List<Son> sons;
	private final List<Daughter> daughters;
	
	public Parent(String id, String name, List<Son> sons, List<Daughter> daughters) {
		super(id);
		this.name = name;
		this.sons = sons;
		this.daughters = daughters;
	}

	public String getName() {
		return name;
	}

	public List<Son> getSons() {
		return sons;
	}

	public List<Daughter> getDaughters() {
		return daughters;
	}
}
