package com.lockdown.persist.store.util;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Identifable;
import com.lockdown.persist.store.DataStore;
import com.lockdown.persist.store.DataStoreFor;

@Service
public class CascadingSaver {
	
	private static final Logger logger = LoggerFactory.getLogger(CascadingSaver.class);
	
	private final ListableBeanFactory beanFactory;
	
	@Autowired
	public CascadingSaver(ListableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	private Map<Class<? extends Identifable>, DataStore<? extends Identifable>> dataStoreMap = new HashMap<>();
	
	@PostConstruct
	@SuppressWarnings("unchecked")
	void retrieveDataStores() {
		Map<String, Object> dataStores = beanFactory.getBeansWithAnnotation(DataStoreFor.class);
		
		for (Object dataStore: dataStores.values()) {
			Class<? extends Identifable> forClass = dataStore.getClass().getAnnotation(DataStoreFor.class).value();
			
			if (dataStore instanceof DataStore) {
				dataStoreMap.put(forClass, (DataStore<? extends Identifable>) dataStore);
			}
			else {
				throw new IllegalStateException("Found object annotated as @DataStoreFor but was not a DataStore instance: " + dataStore.getClass().getName());
			}
		}
		
		logger.info("Found " + dataStoreMap.size() + " data stores: " + dataStoreMap);
	}

	public <T extends Identifable> T saveAndCascade(T object) {

		Objects.requireNonNull(object);
		return saveRootAndCascade(object);
	}

	@SuppressWarnings("unchecked")
	private <T extends Identifable> T saveRootAndCascade(T root) {
		
		try {
			for (Field field: getAllFields(root)) {
				
				field.setAccessible(true);
				Object fieldValue = field.get(root);
				
				if (isFieldDomainObject(field)) {
					Identifable savedField = saveRootAndCascade((Identifable) fieldValue);
					updateField(field, root, savedField);
				}
				else if (isFieldDomainObjectListOrSet(field, root)) {
					Collection<Identifable> collection = (Collection<Identifable>) fieldValue;
					Stream<Identifable> stream = collection.stream().map(this::saveRootAndCascade);
					
					if (collection instanceof Set) {
						updateField(field, root, stream.collect(Collectors.toSet()));
					}
					else {
						updateField(field, root, stream.collect(Collectors.toList()));
					}
				}
			}
			
			return saveDomainObject(root);
		}
		catch (SecurityException | ReflectiveOperationException e) {
			throw new IllegalStateException("Could not cascade saves", e);
		}
	}
	
	private Set<Field> getAllFields(Object object) {
		Set<Field> fieldList = new HashSet<Field>();
		
	    for (Class<?> current = object.getClass(); current.getSuperclass() != null; current = current.getSuperclass()) {
	    	fieldList.addAll(Arrays.asList(current.getDeclaredFields()));
	    }
	    
	    return fieldList;
	}

	private static boolean isFieldDomainObject(Field field) {
		return Identifable.class.isAssignableFrom(field.getType());
	}
	
	private static boolean isFieldDomainObjectListOrSet(Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
		
		if (isListOrSet(field)) {
			Iterable<?> iterable = (Iterable<?>) field.get(object);
			Iterator<?> it = iterable.iterator();

			return it.hasNext() && it.next() instanceof Identifable;
		}
		
		return false;
	}
	
	private static boolean isListOrSet(Field field) {
		Class<?> fieldType = field.getType();
		return List.class.isAssignableFrom(fieldType) || Set.class.isAssignableFrom(fieldType);
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
	private <T extends Identifable> T saveDomainObject(T object) 
			throws IllegalArgumentException, IllegalAccessException {
		
		DataStore<T> dataStore = (DataStore<T>) dataStoreMap.get(object.getClass());
		
		if (dataStore == null) {
			throw new IllegalStateException("Could not find data store for " + object.getClass().getName());
		}
		else {
			return dataStore.save(object);
		}
	}

	Map<Class<? extends Identifable>, DataStore<? extends Identifable>> getFoundDataStores() {
		return dataStoreMap;
	}
}
