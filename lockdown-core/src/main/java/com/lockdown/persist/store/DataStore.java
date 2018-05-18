package com.lockdown.persist.store;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.lockdown.domain.Identifiable;

public interface DataStore<T extends Identifiable> {
	public boolean existsById(String id);
	public List<T> findAll();
	public List<T> findAllById(Iterable<String> ids);
	public Optional<T> findById(String id);
	public T save(T toSave);
	public T saveAndCascade(T toSave);
	public List<T> saveAll(Collection<T> toSave);
	public List<T> saveAllAndCascade(Collection<T> toSave);
	public void deleteById(String id);
}
