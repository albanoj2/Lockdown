package com.lockdown.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.domain.Credentials;
import com.lockdown.persist.store.CredentialsDataStore;
import com.lockdown.rest.error.ResourceNotFoundException;
import com.lockdown.rest.resource.CredentialsResource;
import com.lockdown.rest.resource.assembler.CredentialsResourceAssembler;

@RestController
@ExposesResourceFor(Credentials.class)
@RequestMapping("/credentials")
public class CredentialsController {
	
	@Autowired
	private CredentialsDataStore credentialsDataStore;
	
	@Autowired
	private CredentialsResourceAssembler assembler;

	@GetMapping
	public ResponseEntity<List<CredentialsResource>> getAllCredentials() {
		List<Credentials> credentials = credentialsDataStore.findAll();
		return new ResponseEntity<>(assembler.toResources(credentials), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CredentialsResource> getCredentials(@PathVariable String id) {
		Credentials credentials = credentialsDataStore.findById(id)
			.orElseThrow(ResourceNotFoundException.supplierForResource("credentials", id));
		return new ResponseEntity<>(assembler.toResource(credentials), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CredentialsResource> addCredentials(@RequestBody Credentials credentials) {
		Credentials savedCredentials = credentialsDataStore.saveAndCascade(credentials);
		return new ResponseEntity<>(assembler.toResource(savedCredentials), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CredentialsResource> editCredentials(@PathVariable String id, @RequestBody Credentials credentials) {
		credentials.setId(id);
		Credentials savedCredentials = credentialsDataStore.saveAndCascade(credentials);
		return new ResponseEntity<>(assembler.toResource(savedCredentials), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCredentials(@PathVariable String id) {
		
		if (credentialsDataStore.existsById(id)) {
			credentialsDataStore.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
