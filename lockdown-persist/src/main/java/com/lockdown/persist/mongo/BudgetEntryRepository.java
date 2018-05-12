package com.lockdown.persist.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.budget.BudgetEntry;

public interface BudgetEntryRepository extends MongoRepository<BudgetEntry, Long> {}
