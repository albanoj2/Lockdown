package com.lockdown.persist.store.util.data.cascade.store;

import com.lockdown.persist.store.DataStore;
import com.lockdown.persist.store.DataStoreFor;
import com.lockdown.persist.store.util.data.cascade.domain.Grandchild;

@DataStoreFor(Grandchild.class)
public interface GrandchildDataStore extends DataStore<Grandchild> {

}
