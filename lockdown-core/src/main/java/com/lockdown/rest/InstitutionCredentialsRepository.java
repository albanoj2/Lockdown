package com.lockdown.rest;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lockdown.domain.account.Credentials;

@RepositoryRestResource(collectionResourceRel = "credentials", path = "credentials")
public interface InstitutionCredentialsRepository extends MongoRepository<Credentials, String> {

}
