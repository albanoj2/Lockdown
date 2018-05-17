package com.lockdown.persist.store.util;

import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

import org.mockito.InOrder;

import com.lockdown.persist.store.util.data.cascade.domain.Child;
import com.lockdown.persist.store.util.data.cascade.domain.Grandchild;
import com.lockdown.persist.store.util.data.cascade.domain.Parent;
import com.lockdown.persist.store.util.data.cascade.store.ChildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.GrandchildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.ParentDataStore;

class SaveOrderVerifier {

	private final InOrder order;
	private final ParentDataStore parentDataStore;
	private final ChildDataStore childDataStore;
	private final GrandchildDataStore grandchildDataStore;
	
	public SaveOrderVerifier(InOrder order, ParentDataStore parentDataStore, ChildDataStore childDataStore,
			GrandchildDataStore grandchildDataStore) {
		this.order = inOrder(grandchildDataStore, childDataStore, parentDataStore);
		this.parentDataStore = parentDataStore;
		this.childDataStore = childDataStore;
		this.grandchildDataStore = grandchildDataStore;
	}

	public SaveOrderVerifier assertSavedNext(Parent parent) {
		order.verify(parentDataStore, times(1)).save(eq(parent));
		return this;
	}

	public SaveOrderVerifier assertSavedNext(Child child) {
		order.verify(childDataStore, times(1)).save(eq(child));
		return this;
	}

	public SaveOrderVerifier assertSavedNext(Grandchild grandchild) {
		order.verify(grandchildDataStore, times(1)).save(eq(grandchild));
		return this;
	}
}
