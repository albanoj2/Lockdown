package com.lockdown.persist.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Account;
import com.lockdown.persist.dto.AccountDto;

@Service
@DataStoreFor(Account.class)
public class AccountDataStore extends AbstractDataStore<Account, AccountDto> {
	
	@Autowired
	private TransactionDataStore transactionDataStore;

	@Override
	protected AccountDto fromDomainObject(Account domainObject) {
		return new AccountDto(domainObject);
	}

	@Override
	protected Account toDomainObject(AccountDto dto) {
		return new Account(dto.getId(), dto.getKey(), dto.getName(), dto.getType(), dto.getSubtype(), transactionDataStore.findAllById(dto.getTransactionIds()));
	}
}
