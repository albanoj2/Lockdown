package com.lockdown.persist.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lockdown.account.Account;

@RepositoryRestResource(collectionResourceRel = "account", path = "accounts")
public interface AccountRepository extends MongoRepository<Account, Long> {}
