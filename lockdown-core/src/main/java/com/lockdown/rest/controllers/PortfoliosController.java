package com.lockdown.rest.controllers;

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

import com.lockdown.domain.portfolio.Portfolio;
import com.lockdown.persist.repository.PortfoliosRepository;

@RestController
@RequestMapping("/portfolios")
public class PortfoliosController {

	@Autowired
	private PortfoliosRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Portfolio>> getAllPortfolios() {
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Portfolio> getPortfolio(@PathVariable long id) {
		Optional<Portfolio> portfolio = repository.findById(id);
		
		if (portfolio.isPresent()) {
			return new ResponseEntity<>(portfolio.get(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePortfolio(@PathVariable long id) {
		
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<Portfolio> createPortfolio() {
		Portfolio createdPortfolio = repository.save(new Portfolio());
		return new ResponseEntity<>(createdPortfolio, HttpStatus.CREATED);
	}
}
