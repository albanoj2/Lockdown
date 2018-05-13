package com.lockdown.rest;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.domain.account.UnbudgetedTransaction;

public interface UnbudgetedTransactionRepository extends MongoRepository<UnbudgetedTransaction, Long> {}
