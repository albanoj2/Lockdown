package com.lockdown.persist.store.util.data.cascade.store;

import com.lockdown.persist.store.DataStore;
import com.lockdown.persist.store.DataStoreFor;
import com.lockdown.persist.store.util.data.cascade.domain.Child;

@DataStoreFor(Child.class)
public interface ChildDataStore extends DataStore<Child> {

}
