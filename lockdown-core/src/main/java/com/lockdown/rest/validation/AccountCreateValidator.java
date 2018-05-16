package com.lockdown.rest.validation;

import org.springframework.validation.Errors;

import com.lockdown.domain.account.Account;

public class AccountCreateValidator extends AccountValidator {

	@Override
	protected void validateDomainObject(Account target, Errors errors) {
		 
		if (isEmpty(target.getName())) {
			errors.rejectValue(Field.NAME.getName(), "empty");
		}
	}
}
