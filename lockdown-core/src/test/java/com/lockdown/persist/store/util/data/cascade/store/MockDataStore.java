package com.lockdown.persist.store.util.data.cascade.store;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.lockdown.persist.store.DataStore;
import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;

public class MockDataStore<T extends MockDomainObject> implements DataStore<T> {

	@Override
	public boolean existsById(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> findAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> findAllById(Iterable<String> ids) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<T> findById(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T save(T toSave) {
		T copy = (T) toSave.copy();
		copy.setId("someId");
		return copy;
	}

	@Override
	public T saveAndCascade(T toSave) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> saveAll(Collection<T> toSave) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> saveAllAndCascade(Collection<T> toSave) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteById(String id) {
		throw new UnsupportedOperationException();
	}
}
