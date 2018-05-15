package com.lockdown.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lockdown.domain.account.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

}
