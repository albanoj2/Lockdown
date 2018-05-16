package com.lockdown.persist.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.BudgetItem;
import com.lockdown.persist.dto.BudgetItemDto;
import com.lockdown.persist.repository.BudgetItemRepository;
import com.lockdown.persist.repository.LockdownRepository;

@Service
public class BudgetItemDataStore extends AbstractDataStore<BudgetItem, BudgetItemDto> {
	
	@Autowired
	private BudgetItemRepository repository;

	@Override
	protected BudgetItemDto fromDomainObject(BudgetItem domainObject) {
		return new BudgetItemDto(domainObject);
	}

	@Override
	protected BudgetItem toDomainObject(BudgetItemDto dto) {
		return dto.toBudgetItem();
	}

	@Override
	protected LockdownRepository<BudgetItemDto> getRepository() {
		return repository;
	}

}
