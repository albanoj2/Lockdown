package com.lockdown.rest.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.lockdown.rest.controller.config.MockMongoConfiguration;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountsController.class)
@ComponentScan("com.lockdown.rest")
@ContextConfiguration(classes = {MockMongoConfiguration.class})
public abstract class AbstractControllerTest {

	@Autowired
	private MockMvc mvc;
	
	protected final MockMvc mvc() {
		return mvc;
	}
}
