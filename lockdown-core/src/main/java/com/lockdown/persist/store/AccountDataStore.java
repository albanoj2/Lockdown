package com.lockdown.persist.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Account;
import com.lockdown.domain.Transaction;
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
		return new Account(dto.getId(), dto.getKey(), dto.getName(), dto.getType(), transactionDataStore.findAllById(dto.getTransactionIds()));
	}

	@Override
	public Account saveAndCascade(Account toSave) {
		List<Transaction> savedTransactions = transactionDataStore.saveAllAndCascade(toSave.getTransactions());
		return save(new Account(toSave.getId(), toSave.getKey(), toSave.getName(), toSave.getType(), savedTransactions));
	}
}
