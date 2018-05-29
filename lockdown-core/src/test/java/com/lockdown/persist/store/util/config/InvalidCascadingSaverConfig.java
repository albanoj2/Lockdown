package com.lockdown.persist.store.util.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lockdown.domain.Identifiable;
import com.lockdown.persist.store.DataStoreFor;

@Configuration
public class InvalidCascadingSaverConfig {
	
	@Bean
	public InvalidDataStore invalidDataStore() {
		return new InvalidDataStore();
	}

	@DataStoreFor(SomeDomainObject.class)
	private static class InvalidDataStore {}
	
	public static class SomeDomainObject extends Identifiable {}
}
