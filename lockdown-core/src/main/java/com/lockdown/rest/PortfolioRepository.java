package com.lockdown.rest;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.domain.portfolio.Portfolio;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {}
