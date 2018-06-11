package com.lockdown.persist.store;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lockdown.domain.Identifiable;
import com.lockdown.persist.dto.Dto;
import com.lockdown.persist.repository.LockdownRepository;
import com.lockdown.persist.store.util.CascadingSaver;

public abstract class AbstractDataStore<DomainObjectType extends Identifiable, DtoType extends Dto> implements DataStore<DomainObjectType> {
	
	@Autowired
	private LockdownRepository<DtoType> repository;
	
	@Autowired
	private CascadingSaver saver;

	@Override
	public final boolean existsById(String id) {
		return repository.existsById(id);
	}
	
	@Override
	public final List<DomainObjectType> findAll() {
		return repository.findAll().stream()
			.map(this::toDomainObject)
			.collect(Collectors.toList());
	}
	
	@Override
	public final Page<DomainObjectType> findAll(Pageable pageable) {
		return repository.findAll(pageable)
			.map(this::toDomainObject);
	}

	@Override
	public final List<DomainObjectType> findAllById(Iterable<String> ids) {
		return StreamSupport.stream(repository.findAllById(ids).spliterator(), false)
			.map(this::toDomainObject)
			.collect(Collectors.toList());
	}

	@Override
	public Page<DomainObjectType> findAllById(Iterable<String> ids, Pageable pageable) {
		return repository.findByIdIn(ids, pageable)
			.map(this::toDomainObject);
	}

	@Override
	public final Optional<DomainObjectType> findById(String id) {
		return repository.findById(id).stream()
			.map(this::toDomainObject)
			.findFirst();
	}

	@Override
	public DomainObjectType save(DomainObjectType toSave) {
		DtoType dto = repository.save(fromDomainObject(toSave));
		return toDomainObject(dto);
	}

	@Override
	public List<DomainObjectType> saveAll(Collection<DomainObjectType> toSave) {
		return toSave.stream()
			.map(this::save)
			.collect(Collectors.toList());
	}

	@Override
	public List<DomainObjectType> saveAllAndCascade(Collection<DomainObjectType> toSave) {
		return toSave.stream()
			.map(this::saveAndCascade)
			.collect(Collectors.toList());
	}

	@Override
	public final void deleteById(String id) {
		repository.deleteById(id);
	}
	
	@Override
	public DomainObjectType saveAndCascade(DomainObjectType toSave) {
		return saver.saveAndCascade(toSave);
	}

	protected LockdownRepository<DtoType> getRepository() {
		return repository;
	}

	protected abstract DtoType fromDomainObject(DomainObjectType domainObject);
	protected abstract DomainObjectType toDomainObject(DtoType dto);
}
