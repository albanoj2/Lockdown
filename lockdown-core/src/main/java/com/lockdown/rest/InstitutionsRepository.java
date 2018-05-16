package com.lockdown.rest;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lockdown.domain.account.Institution;

@RepositoryRestResource(collectionResourceRel = "institutions", path = "institutions")
public interface InstitutionsRepository extends MongoRepository<Institution, String> {}
