package com.lockdown.rest;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.domain.budget.BudgetItem;

public interface BudgetItemRepository extends MongoRepository<BudgetItem, String> {}
