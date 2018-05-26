package com.lockdown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource("file:/opt/plaid/plaid-config.properties")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
