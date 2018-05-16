package com.lockdown.persist.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.lockdown.domain.DomainObject;
import com.lockdown.persist.dto.Dto;
import com.lockdown.persist.repository.LockdownRepository;

public abstract class AbstractDataStore<T extends DomainObject, S extends Dto> implements DataStore<T> {

	@Override
	public final boolean existsById(String id) {
		return getRepository().existsById(id);
	}
	
	@Override
	public final List<T> findAll() {
		return getRepository().findAll().stream()
			.map(this::toDomainObject)
			.collect(Collectors.toList());
	}

	@Override
	public final List<T> findAllById(List<String> ids) {
		return StreamSupport.stream(getRepository().findAllById(ids).spliterator(), false)
			.map(this::toDomainObject)
			.collect(Collectors.toList());
	}

	@Override
	public final Optional<T> findById(String id) {
		return getRepository().findById(id).stream()
			.map(this::toDomainObject)
			.findFirst();
	}

	@Override
	public T save(T toSave) {
		S dto = getRepository().save(fromDomainObject(toSave));
		return toDomainObject(dto);
	}

	@Override
	public List<T> saveAll(List<T> toSave) {
		List<T> saved = new ArrayList<>();
		
		for (T object: toSave) {
			T savedObject = save(object);
			saved.add(savedObject);
		}
		
		return saved;
	}

	@Override
	public final void deleteById(String id) {
		getRepository().deleteById(id);
	}
	
	protected abstract S fromDomainObject(T domainObject);
	protected abstract T toDomainObject(S dto);
	protected abstract LockdownRepository<S> getRepository();
}
