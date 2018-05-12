package com.lockdown.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.lockdown.persist.mongo")
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
