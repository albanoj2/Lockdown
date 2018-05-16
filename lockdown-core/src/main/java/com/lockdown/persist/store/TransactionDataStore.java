package com.lockdown.persist.store;

import org.springframework.stereotype.Service;

import com.lockdown.domain.Transaction;
import com.lockdown.persist.dto.TransactionDto;

@Service
@DataStoreFor(Transaction.class)
public class TransactionDataStore extends AbstractDataStore<Transaction, TransactionDto> {

	@Override
	protected TransactionDto fromDomainObject(Transaction domainObject) {
		return new TransactionDto(domainObject);
	}

	@Override
	protected Transaction toDomainObject(TransactionDto dto) {
		return dto.toTransaction();
	}

	@Override
	public Transaction saveAndCascade(Transaction toSave) {
		return save(toSave);
	}
}
