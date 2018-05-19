package com.lockdown.persist.store.util.data.cascade.store;

import com.lockdown.persist.store.DataStoreFor;
import com.lockdown.persist.store.util.data.cascade.domain.Child;

@DataStoreFor(Child.class)
public class ChildDataStore extends MockDataStore<Child> {

}
