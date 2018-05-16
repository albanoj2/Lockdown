package com.lockdown.rest.validation;

public class AccountCreateValidatorTest {

//	private AccountCreateValidator validator;
//	private Errors errors;
//	
//	@Before
//	public void setUp() {
//		this.validator = new AccountCreateValidator();
//		this.errors = new MapBindingResult(new HashMap<>(), "DomainObject");
//	}
//	
//	@Test
//	public void givenValidAccountWhenValidateEnsureNoErrors() {
//		Account account = new Account("1", "Some name");
//		validate(account);
//		assertNoErrors();
//	}
//	
//	private void validate(Account account) {
//		validator.validate(account, errors);
//	}
//	
//	private void assertNoErrors() {
//		assertFalse(errors.hasErrors());
//	}
//	
//	@Test
//	public void givenAccountWithNullNameWhenValidateEnsureErrorsFound() {
//		Account account = new Account("1", null);
//		validate(account);
//		assertErrorFound(Field.NAME.getName());
//	}
//	
//	private void assertErrorFound(String key) {
//		assertNotNull(errors.getFieldError(key));
//	}
//	
//	@Test
//	public void givenAccountWithEmptyNameWhenValidateEnsureErrorsFound() {
//		Account account = new Account("1", "");
//		validate(account);
//		assertErrorFound(Field.NAME.getName());
//	}
//	
//	@Test
//	public void givenAccountWithWhitespaceNameWhenValidateEnsureErrorsFound() {
//		Account account = new Account("1", " ");
//		validate(account);
//		assertErrorFound(Field.NAME.getName());
//	}
}
