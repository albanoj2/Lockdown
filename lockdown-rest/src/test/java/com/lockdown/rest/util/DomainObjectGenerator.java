package com.lockdown.rest.util;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lockdown.DomainObject;

public abstract class DomainObjectGenerator<T extends DomainObject> implements Supplier<T> {

	private AtomicLong ID = new AtomicLong();

	@Override
	public T get() {
		return createInstanceWithId(ID.getAndIncrement());
	}
	
	protected abstract T createInstanceWithId(long id);
	
	public List<T> listOf(int number) {
		return Stream.generate(this).limit(number).collect(Collectors.toList());
	}
}
