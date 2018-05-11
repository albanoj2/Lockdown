package com.lockdown.persist;

import java.util.List;
import java.util.Optional;

import com.lockdown.DomainObject;

public interface Repository<T extends DomainObject> {
	public long create(T object);
	public List<T> findAll();
	public Optional<T> find(long id);
	public void update(long id, T updated);
	public T delete(long id) throws DomainObjectNotFoundException;
}
