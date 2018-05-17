package com.lockdown.persist.store.util.helper;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;

@Aspect
public class DataStoresWatcher {
	
	private final List<MockDomainObject> savedObjects;
	private final List<OnSaveListener> onSaveListeners;
	
	public DataStoresWatcher() {
		this.savedObjects = new ArrayList<>();
		this.onSaveListeners = new ArrayList<>();
	}
	
	public void clearSavedObjects() {
		savedObjects.clear();
	}
	
	public List<MockDomainObject> getSavedObjects() {
		return savedObjects;
	}

	@AfterReturning(pointcut = "execution(public * com.lockdown.persist.store.util.data.cascade.store.*.save(..))", returning = "savedObject")
	public void onSave(MockDomainObject savedObject) {
		
		if (savedObject != null) {
			savedObjects.add(savedObject);
			
			for (OnSaveListener listener: onSaveListeners) {
				listener.onSave(savedObject);
			}
		}
	}
	
	public void assertOrder(OrderAssertionStrategy strategy) {
		strategy.assertOrder(savedObjects);
	}
	
	public void registerOnSaveListener(OnSaveListener listener) {
		onSaveListeners.add(listener);
	}
}
