package com.lockdown.persist.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lockdown.domain.BudgetItem;
import com.lockdown.domain.BudgetItemMapping;
import com.lockdown.domain.Money;
import com.lockdown.domain.Transaction;
import com.lockdown.persist.dto.BudgetItemMappingDto;
import com.lockdown.persist.dto.TransactionDto;

@Service
@DataStoreFor(Transaction.class)
public class TransactionDataStore extends AbstractDataStore<Transaction, TransactionDto> {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionDataStore.class);
	
	@Autowired
	private BudgetItemDataStore budgetItemDataStore;

	@Override
	protected TransactionDto fromDomainObject(Transaction domainObject) {
		return new TransactionDto(domainObject);
	}

	@Override
	protected Transaction toDomainObject(TransactionDto dto) {
		return new Transaction(
			dto.getId(),
			dto.getDate(), 
			Money.cents(dto.getAmountInCents()),
			dto.getKey(),
			dto.getName(),
			dto.getDescription(),
			dto.isPending(),
			Optional.ofNullable(dto.getComment()),
			generateBudgetItemMapping(dto.getMapping())
		);
	}
	
	private Optional<BudgetItemMapping> generateBudgetItemMapping(BudgetItemMappingDto dto) {
		
		if (dto == null || dto.getMappings() == null) {
			return Optional.empty();
		}
		
		Map<BudgetItem, Money> mappings = new HashMap<>();
		
		for (Entry<String, Long> originalMapping: dto.getMappings().entrySet()) {
			Optional<BudgetItem> key = budgetItemDataStore.findById(originalMapping.getKey());
			
			if (key.isPresent()) {
				Money value = Money.cents(originalMapping.getValue());
				mappings.put(key.get(), value);
			}
			else {
				logger.warn("Cannot find budget item with ID " + originalMapping.getKey() + " to construct budget item mapping");
			}
		}
		
		BudgetItemMapping mapping = new BudgetItemMapping(mappings);
		return Optional.of(mapping);
	}
}
