package com.lockdown.rest;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.domain.budget.BudgetEntry;

public interface BudgetEntryRepository extends MongoRepository<BudgetEntry, Long> {}
