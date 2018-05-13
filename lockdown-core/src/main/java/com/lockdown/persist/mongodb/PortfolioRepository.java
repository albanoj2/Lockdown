package com.lockdown.persist.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.portfolio.Portfolio;

public interface PortfolioRepository extends MongoRepository<Portfolio, Long> {}
