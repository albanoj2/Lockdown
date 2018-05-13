package com.lockdown.rest;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.domain.budget.Budget;

public interface BudgetRepository extends MongoRepository<Budget, Long> {}
