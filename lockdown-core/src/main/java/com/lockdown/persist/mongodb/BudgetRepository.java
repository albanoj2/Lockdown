package com.lockdown.persist.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.budget.Budget;

public interface BudgetRepository extends MongoRepository<Budget, Long> {}
