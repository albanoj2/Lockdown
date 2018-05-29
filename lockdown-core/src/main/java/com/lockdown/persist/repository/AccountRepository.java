package com.lockdown.persist.repository;

import java.util.Optional;

import com.lockdown.persist.dto.AccountDto;

public interface AccountRepository extends LockdownRepository<AccountDto> {
	public Optional<AccountDto> findByKey(String key);
}
