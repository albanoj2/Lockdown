package com.lockdown.persist.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;

import com.lockdown.domain.Identifiable;

public abstract class Dto {

	@Id
	private final String id;
	
	protected Dto(String id) {
		this.id = id;
	}
	
	protected Dto() {
		this(null);
	}
	
	public String getId() {
		return id;
	}
	
	protected static List<String> toIdList(List<? extends Identifiable> objects) {
		return objects.stream()
			.map(o -> o.getId())
			.collect(Collectors.toList());
	}
}
