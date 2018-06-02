package com.lockdown.persist.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Budget;
import com.lockdown.persist.dto.BudgetDto;

@Service
@DataStoreFor(Budget.class)
public class BudgetDataStore extends AbstractDataStore<Budget, BudgetDto> {
	
	@Autowired
	private BudgetItemDataStore budgetItemDataStore;

	@Override
	protected BudgetDto fromDomainObject(Budget domainObject) {
		return new BudgetDto(domainObject);
	}

	@Override
	protected Budget toDomainObject(BudgetDto dto) {
		return new Budget(dto.getId(), dto.getName(), dto.getDescription(), budgetItemDataStore.findAllById(dto.getEntryIds()));
	}

}
