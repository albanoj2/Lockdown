package com.lockdown.persist.store.util.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.lockdown.persist.store.util.data.cascade.store.ChildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.DomainSubclassDataStore;
import com.lockdown.persist.store.util.data.cascade.store.GrandchildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.ParentDataStore;
import com.lockdown.persist.store.util.helper.DataStoresWatcher;

@Configuration
@ComponentScan(basePackages = "com.lockdown.persist.store.util")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CascadingSaverConfig {

	@Bean
	public ParentDataStore parentDataStore() {
		return new ParentDataStore();
	}
	
	@Bean
	public ChildDataStore childDataStore() {
		return new ChildDataStore();
	}
	
	@Bean
	public GrandchildDataStore grandchildDataStore() {
		return new GrandchildDataStore();
	}
	
	@Bean
	public DomainSubclassDataStore domainSubclassDataStore() {
		return new DomainSubclassDataStore();
	}
	
	@Bean
	public DataStoresWatcher dataStoresWatcher() {
		return new DataStoresWatcher();
	}
}
