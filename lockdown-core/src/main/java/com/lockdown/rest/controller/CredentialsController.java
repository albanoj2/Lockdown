package com.lockdown.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.domain.Credentials;
import com.lockdown.persist.store.CredentialsDataStore;

@RestController
@ExposesResourceFor(Credentials.class)
@RequestMapping("/credentials")
public class CredentialsController {
	
	@Autowired
	private CredentialsDataStore credentialsDataStore;

	@GetMapping
	public ResponseEntity<List<Credentials>> getCredentials() {
		List<Credentials> credentials = credentialsDataStore.findAll();
		return new ResponseEntity<>(credentials, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Credentials> addCredentials(@RequestBody Credentials credentials) {
		Credentials savedCredentials = credentialsDataStore.saveAndCascade(credentials);
		return new ResponseEntity<>(savedCredentials, HttpStatus.OK);
	}
}
