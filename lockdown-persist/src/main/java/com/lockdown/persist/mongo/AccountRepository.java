package com.lockdown.persist.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.account.Account;

public interface AccountRepository extends MongoRepository<Account, Long> {}
