package com.lockdown.rest.controllers;

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

import com.lockdown.domain.account.Credentials;
import com.lockdown.domain.portfolio.Portfolio;
import com.lockdown.persist.repository.CredentialsRepository;
import com.lockdown.persist.repository.PortfoliosRepository;

@RestController
@RequestMapping("/portfolios/{portfolioId}/credentials")
public class CredentialsController {
	
	@Autowired
	private PortfoliosRepository portfoliosRepository;
	
	@Autowired
	private CredentialsRepository credentialsRepository;

	@GetMapping
	public ResponseEntity<List<Credentials>> getCredentials(@PathVariable long portfolioId) {
		Optional<Portfolio> portfolio = portfoliosRepository.findById(portfolioId);
		
		if (portfolio.isPresent()) {
			return new ResponseEntity<>(portfolio.get().getCredentials(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<Credentials> addCredentials(@PathVariable long portfolioId, @RequestBody Credentials credentials) {
		Optional<Portfolio> portfolio = portfoliosRepository.findById(portfolioId);
		
		if (portfolio.isPresent()) {
			Credentials createdCredentials = credentialsRepository.save(credentials);
			portfolio.get().getCredentials().add(createdCredentials);
			portfoliosRepository.save(portfolio.get());
			return new ResponseEntity<>(createdCredentials, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
