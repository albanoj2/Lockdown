package com.lockdown.persist.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.account.BudgetedTransaction;

public interface BudgetedTransactionRepository extends MongoRepository<BudgetedTransaction, Long> {}
