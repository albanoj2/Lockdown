package com.lockdown.persist.store;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.lockdown.domain.DomainObject;

@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface DataStoreFor {
	Class<? extends DomainObject> value();
}
