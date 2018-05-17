package com.lockdown.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockdown.domain.Portfolio;
import com.lockdown.persist.store.PortfolioDataStore;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
	
	@Autowired
	private PortfolioDataStore dataStore;

	@GetMapping
	public ResponseEntity<List<Portfolio>> getAllPortfolios() {
		return new ResponseEntity<>(dataStore.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Portfolio> createPortfolio() {
		Portfolio createdPortfolio = dataStore.saveAndCascade(new Portfolio());
		return new ResponseEntity<>(createdPortfolio, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Portfolio> getPortfolio(@PathVariable String id) {
		
		Optional<Portfolio> portfolio = dataStore.findById(id);
		
		if (portfolio.isPresent()) {
			return new ResponseEntity<>(portfolio.get(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePortfolio(@PathVariable String id) {
		
		if (dataStore.existsById(id)) {
			dataStore.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
