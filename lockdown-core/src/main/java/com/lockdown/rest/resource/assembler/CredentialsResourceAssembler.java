package com.lockdown.rest.resource.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lockdown.domain.Credentials;
import com.lockdown.rest.controller.CredentialsController;
import com.lockdown.rest.resource.CredentialsResource;

@Component
public class CredentialsResourceAssembler extends ResourceAssemblerSupport<Credentials, CredentialsResource> {

	public CredentialsResourceAssembler() {
		super(CredentialsController.class, CredentialsResource.class);
	}

	@Override
	public CredentialsResource toResource(Credentials credentials) {
		return new CredentialsResource(credentials);
	}

}
