package com.lockdown.persist.store.util;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.DomainObject;
import com.lockdown.persist.store.DataStore;
import com.lockdown.persist.store.DataStoreFor;

@Service
public final class CascadingSaver {
	
	private static final Logger logger = LoggerFactory.getLogger(CascadingSaver.class);
	
	@Autowired
	private ListableBeanFactory beanFactory;
	
	private Map<Class<? extends DomainObject>, DataStore<? extends DomainObject>> dataStoreMap = new HashMap<>();
	
	@PostConstruct
	@SuppressWarnings("unchecked")
	private void retrieveDataStores() {
		Map<String, Object> dataStores = beanFactory.getBeansWithAnnotation(DataStoreFor.class);
		
		for (Object dataStore: dataStores.values()) {
			Class<? extends DomainObject> forClass = dataStore.getClass().getAnnotation(DataStoreFor.class).value();
			
			if (dataStore instanceof DataStore) {
				dataStoreMap.put(forClass, (DataStore<? extends DomainObject>) dataStore);
			}
			else {
				throw new IllegalStateException("Found object annotated as @DataStoreFor but was not a DataStore instance: " + dataStore.getClass().getName());
			}
		}
		
		logger.info("Found " + dataStoreMap.size() + " data stores: " + dataStoreMap);
	}

	public <T extends DomainObject> T saveAndCascade(T object) {

		Objects.requireNonNull(object);
		
		try {
			return saveDomainObjectAndCascade(object);
		}
		catch (SecurityException | ReflectiveOperationException e) {
			throw new IllegalStateException("Could not cascade saves", e);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends DomainObject> T saveDomainObjectAndCascade(T domainObject) throws SecurityException, ReflectiveOperationException {
		
		for (Field field: getAllFields(domainObject)) {
			
			field.setAccessible(true);
			Object fieldValue = field.get(domainObject);
			
			if (isFieldDomainObject(field)) {
				DomainObject savedField = saveDomainObjectAndCascade((DomainObject) fieldValue);
				updateField(field, domainObject, savedField);
			}
			else if (isFieldDomainObjectIterable(field, domainObject)) {
				Iterable<DomainObject> iterable = (Iterable<DomainObject>) fieldValue;
				List<DomainObject> savedDomainObjects = new ArrayList<>();
				
				for (DomainObject subDomainObject: iterable) {
					DomainObject savedDomainObject = saveDomainObjectAndCascade(subDomainObject);
					savedDomainObjects.add(savedDomainObject);
				}
				
				updateField(field, domainObject, savedDomainObjects);
			}
		}
		
		return saveDomainObject(domainObject);
	}
	
	private Set<Field> getAllFields(Object object) {
		Set<Field> fieldList = new HashSet<Field>();
		
	    for (Class<?> current = object.getClass(); current.getSuperclass() != null; current = current.getSuperclass()) {
	    	fieldList.addAll(Arrays.asList(current.getDeclaredFields()));
	    }
	    
	    return fieldList;
	}

	private static boolean isFieldDomainObject(Field field) {
		return DomainObject.class.isAssignableFrom(field.getType());
	}
	
	private static boolean isFieldDomainObjectIterable(Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
		
		if (isIterable(field)) {
			Iterable<?> iterable = (Iterable<?>) field.get(object);
			Iterator<?> it = iterable.iterator();

			return it.hasNext() && it.next() instanceof DomainObject;
		}
		
		return false;
	}
	
	private static boolean isIterable(Field field) {
		return Iterable.class.isAssignableFrom(field.getType());
	}

	private void updateField(Field field, Object object, Object newValue)
			throws IllegalAccessException, NoSuchFieldException, SecurityException {
		allowAccessTo(field);
		field.set(object, newValue);
	}

	private void allowAccessTo(Field field) throws NoSuchFieldException, IllegalAccessException {
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends DomainObject> T saveDomainObject(T object) 
			throws IllegalArgumentException, IllegalAccessException {
		
		DataStore<T> dataStore = (DataStore<T>) dataStoreMap.get(object.getClass());
		
		if (dataStore == null) {
			throw new IllegalStateException("Could not find data store for " + object.getClass().getName());
		}
		else {
			return dataStore.save(object);
		}
	}

	Map<Class<? extends DomainObject>, DataStore<? extends DomainObject>> getFoundDataStores() {
		return dataStoreMap;
	}
}
