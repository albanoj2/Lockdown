package com.lockdown.rest;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lockdown.domain.account.InstitutionCredentials;

@RepositoryRestResource(collectionResourceRel = "credentials", path = "credentials")
public interface InstitutionCredentialsRepository extends MongoRepository<InstitutionCredentials, String> {

}
