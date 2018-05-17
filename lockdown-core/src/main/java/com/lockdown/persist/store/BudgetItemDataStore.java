package com.lockdown.persist.store;

import org.springframework.stereotype.Service;

import com.lockdown.domain.BudgetItem;
import com.lockdown.persist.dto.BudgetItemDto;

@Service
@DataStoreFor(BudgetItem.class)
public class BudgetItemDataStore extends AbstractDataStore<BudgetItem, BudgetItemDto> {

	@Override
	protected BudgetItemDto fromDomainObject(BudgetItem domainObject) {
		return new BudgetItemDto(domainObject);
	}

	@Override
	protected BudgetItem toDomainObject(BudgetItemDto dto) {
		return dto.toBudgetItem();
	}
}
