package com.lockdown.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lockdown.domain.budget.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {}
