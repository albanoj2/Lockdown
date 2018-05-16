package com.lockdown.persist.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lockdown.persist.dto.Dto;

public interface LockdownRepository<T extends Dto> extends MongoRepository<T, String> {}
