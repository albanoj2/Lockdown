package com.lockdown.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lockdown.domain.account.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

}
