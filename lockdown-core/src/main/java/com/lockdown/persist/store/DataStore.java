package com.lockdown.persist.store;

import java.util.List;
import java.util.Optional;

import com.lockdown.domain.DomainObject;

public interface DataStore<T extends DomainObject> {
	public boolean existsById(String id);
	public List<T> findAll();
	public List<T> findAllById(List<String> ids);
	public Optional<T> findById(String id);
	public T save(T toSave);
	public List<T> saveAll(List<T> toSave);
	public void deleteById(String id);
}
