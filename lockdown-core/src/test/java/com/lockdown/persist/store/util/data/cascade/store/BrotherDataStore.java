package com.lockdown.persist.store.util.data.cascade.store;

import com.lockdown.persist.store.DataStore;
import com.lockdown.persist.store.DataStoreFor;
import com.lockdown.persist.store.util.data.cascade.domain.Brother;

@DataStoreFor(Brother.class)
public interface BrotherDataStore extends DataStore<Brother> {

}
