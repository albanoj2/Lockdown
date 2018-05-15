package com.lockdown.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lockdown.domain.budget.BudgetItem;

public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long> {}
