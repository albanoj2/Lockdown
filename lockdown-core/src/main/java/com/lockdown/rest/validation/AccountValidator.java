package com.lockdown.rest.validation;

import com.lockdown.domain.account.Account;

public abstract class AccountValidator extends DomainObjectValidator<Account> {
	
	public static enum Field {
		
		NAME("name");
		
		private final String name;
		
		private Field(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}

	@Override
	protected Class<?> supportedClass() {
		return Account.class;
	}
}
