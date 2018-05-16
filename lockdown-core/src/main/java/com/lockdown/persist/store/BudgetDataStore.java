package com.lockdown.persist.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Budget;
import com.lockdown.persist.dto.BudgetDto;
import com.lockdown.persist.repository.BudgetRepository;
import com.lockdown.persist.repository.LockdownRepository;

@Service
public class BudgetDataStore extends AbstractDataStore<Budget, BudgetDto> {
	
	@Autowired
	private BudgetRepository repository;
	
	@Autowired
	private BudgetItemDataStore budgetItemDataStore;

	@Override
	protected BudgetDto fromDomainObject(Budget domainObject) {
		return new BudgetDto(domainObject);
	}

	@Override
	protected Budget toDomainObject(BudgetDto dto) {
		return new Budget(dto.getId(), budgetItemDataStore.findAllById(dto.getEntryIds()));
	}

	@Override
	protected LockdownRepository<BudgetDto> getRepository() {
		return repository;
	}

}
