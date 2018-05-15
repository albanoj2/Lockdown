package com.lockdown.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lockdown.domain.account.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {}
