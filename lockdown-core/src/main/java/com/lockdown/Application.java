package com.lockdown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.lockdown.rest.validation.AccountCreateValidator;

@SpringBootApplication
public class Application extends RepositoryRestConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener listener) {
		listener.addValidator("beforeCreate", new AccountCreateValidator());
	}
}
