package com.lockdown.persist.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Account;
import com.lockdown.persist.dto.AccountDto;
import com.lockdown.persist.repository.AccountRepository;
import com.lockdown.persist.repository.LockdownRepository;

@Service
public class AccountDataStore extends AbstractDataStore<Account, AccountDto> {
	
	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private TransactionDataStore transactionDataStore;

	@Override
	protected AccountDto fromDomainObject(Account domainObject) {
		return new AccountDto(domainObject);
	}

	@Override
	protected Account toDomainObject(AccountDto dto) {
		return new Account(dto.getId(), dto.getKey(), dto.getName(), dto.getType(), transactionDataStore.findAllById(dto.getTransactionIds()));
	}

	@Override
	protected LockdownRepository<AccountDto> getRepository() {
		return repository;
	}

}
