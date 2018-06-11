package com.lockdown.persist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lockdown.persist.dto.TransactionDto;

public interface TransactionRepository extends LockdownRepository<TransactionDto> {
	public Page<TransactionDto> findByIdInOrderByDateDesc(Iterable<String> ids, Pageable pageable);
}
