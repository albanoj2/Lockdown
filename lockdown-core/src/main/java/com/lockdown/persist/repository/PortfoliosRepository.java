package com.lockdown.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lockdown.domain.portfolio.Portfolio;

public interface PortfoliosRepository extends JpaRepository<Portfolio, Long> {}
