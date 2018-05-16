package com.lockdown.persist.store.util.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.lockdown.persist.store.util.data.cascade.store.BrotherDataStore;
import com.lockdown.persist.store.util.data.cascade.store.GrandchildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.ParentDataStore;
import com.lockdown.persist.store.util.data.cascade.store.SisterDataStore;

@Configuration
@ComponentScan(basePackages = "com.lockdown.persist.store.util")
public class CascadingSaverConfig {

	@Bean
	public ParentDataStore parentDataStore() {
		return Mockito.mock(ParentDataStore.class);
	}
	
	@Bean
	public BrotherDataStore brotherDataStore() {
		return Mockito.mock(BrotherDataStore.class);
	}
	
	@Bean
	public SisterDataStore sisterDataStore() {
		return Mockito.mock(SisterDataStore.class);
	}
	
	@Bean
	public GrandchildDataStore grandchildDataStore() {
		return Mockito.mock(GrandchildDataStore.class);
	}
}
