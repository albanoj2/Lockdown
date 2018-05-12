package com.lockdown.rest.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@EnableMongoRepositories
public class MockMongoConfiguration {

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(new MongoClient("localhost"), "test");
	}
}
