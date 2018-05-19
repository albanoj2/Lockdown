package com.lockdown.persist.store.util.data.cascade.store;

import com.lockdown.persist.store.DataStoreFor;
import com.lockdown.persist.store.util.data.cascade.domain.Parent;

@DataStoreFor(Parent.class)
public class ParentDataStore extends MockDataStore<Parent> {

}
