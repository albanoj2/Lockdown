package com.lockdown.persist.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Transaction;
import com.lockdown.persist.dto.TransactionDto;
import com.lockdown.persist.repository.LockdownRepository;
import com.lockdown.persist.repository.TransactionRepository;

@Service
public class TransactionDataStore extends AbstractDataStore<Transaction, TransactionDto> {
	
	@Autowired
	private TransactionRepository repository;

	@Override
	protected TransactionDto fromDomainObject(Transaction domainObject) {
		return new TransactionDto(domainObject);
	}

	@Override
	protected Transaction toDomainObject(TransactionDto dto) {
		return dto.toTransaction();
	}

	@Override
	protected LockdownRepository<TransactionDto> getRepository() {
		return repository;
	}
}
