package com.lockdown.rest;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.domain.account.BudgetedTransaction;

public interface BudgetedTransactionRepository extends MongoRepository<BudgetedTransaction, Long> {}
