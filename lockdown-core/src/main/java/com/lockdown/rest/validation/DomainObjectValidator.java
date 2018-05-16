package com.lockdown.rest.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.lockdown.domain.DomainObject;

public abstract class DomainObjectValidator<T extends DomainObject> implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return supportedClass().equals(clazz);
	}
	
	protected abstract Class<?> supportedClass();

	@Override
	@SuppressWarnings("unchecked")
	public void validate(Object target, Errors errors) {
		
		if (target.getClass().equals(supportedClass())) {
			validateDomainObject((T) target, errors);
		}
		else {
			throw new IllegalArgumentException("Supplied object not supported by this validator");
		}
	}
	
	protected abstract void validateDomainObject(T target, Errors errors);
	
	protected static boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

}
