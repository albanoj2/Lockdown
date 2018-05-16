package com.lockdown.persist.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.Budget;
import com.lockdown.domain.BudgetItem;
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
		return new Budget(dto.getId(), budgetItemDataStore.findAllById(dto.getEntryIds()));
	}

	@Override
	public Budget saveAndCascade(Budget toSave) {
		List<BudgetItem> savedBudgetItems = budgetItemDataStore.saveAllAndCascade(toSave.getEntries());
		return save(new Budget(toSave.getId(), savedBudgetItems));
	}

}
