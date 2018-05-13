package com.lockdown.persist.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.account.UnbudgetedTransaction;

public interface UnbudgetedTransactionRepository extends MongoRepository<UnbudgetedTransaction, Long> {}
