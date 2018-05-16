package com.lockdown.persist.store.util.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.lockdown.persist.store.util.data.cascade.store.SonDataStore;
import com.lockdown.persist.store.util.data.cascade.store.GrandchildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.ParentDataStore;
import com.lockdown.persist.store.util.data.cascade.store.DaughterDataStore;

@Configuration
@ComponentScan(basePackages = "com.lockdown.persist.store.util")
public class CascadingSaverConfig {

	@Bean
	public ParentDataStore parentDataStore() {
		return Mockito.mock(ParentDataStore.class);
	}
	
	@Bean
	public SonDataStore brotherDataStore() {
		return Mockito.mock(SonDataStore.class);
	}
	
	@Bean
	public DaughterDataStore sisterDataStore() {
		return Mockito.mock(DaughterDataStore.class);
	}
	
	@Bean
	public GrandchildDataStore grandchildDataStore() {
		return Mockito.mock(GrandchildDataStore.class);
	}
}
