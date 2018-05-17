package com.lockdown.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.domain.Credentials;
import com.lockdown.domain.Portfolio;
import com.lockdown.persist.store.CredentialsDataStore;
import com.lockdown.persist.store.PortfolioDataStore;

@RestController
@RequestMapping("/portfolio/{portfolioId}/credentials")
public class CredentialsController {
	
	@Autowired
	private PortfolioDataStore portfolioDataStore;
	
	@Autowired
	private CredentialsDataStore credentialsDataStore;

	@GetMapping
	public ResponseEntity<List<Credentials>> getCredentials(@PathVariable String portfolioId) {
		
		Optional<Portfolio> portfolio = portfolioDataStore.findById(portfolioId);
		
		if (portfolio.isPresent()) {
			return new ResponseEntity<>(portfolio.get().getCredentials(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<Credentials> addCredentials(@PathVariable String portfolioId, @RequestBody Credentials credentials) {

		Optional<Portfolio> portfolio = portfolioDataStore.findById(portfolioId);
		
		if (portfolio.isPresent()) {
			Credentials savedCredentials = credentialsDataStore.saveAndCascade(credentials);
			portfolio.get().addCredentials(savedCredentials);
			portfolioDataStore.save(portfolio.get());
			
			return new ResponseEntity<>(savedCredentials, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
