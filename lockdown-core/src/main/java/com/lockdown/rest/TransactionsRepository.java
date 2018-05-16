package com.lockdown.rest;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lockdown.domain.account.Transaction;

@RepositoryRestResource(collectionResourceRel = "transactionss", path = "transactions")
public interface TransactionsRepository extends MongoRepository<Transaction, String> {

}
