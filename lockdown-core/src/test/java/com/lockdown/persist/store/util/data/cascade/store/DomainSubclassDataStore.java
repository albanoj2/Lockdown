package com.lockdown.persist.store.util.data.cascade.store;

import com.lockdown.persist.store.DataStoreFor;
import com.lockdown.persist.store.util.data.cascade.domain.DomainSubclass;

@DataStoreFor(DomainSubclass.class)
public class DomainSubclassDataStore extends MockDataStore<DomainSubclass> {

}
