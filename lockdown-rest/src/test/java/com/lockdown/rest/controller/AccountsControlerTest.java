package com.lockdown.rest.controller;

import static com.lockdown.rest.util.Matchers.jsonPathLong;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lockdown.account.Account;
import com.lockdown.persist.mongo.AccountRepository;
import com.lockdown.rest.util.DomainObjectGenerator;


public class AccountsControlerTest extends AbstractControllerTest {
	
	private AccountGenerator generator;
	
	@MockBean
	private AccountRepository accountRepository;
	
	@Before
	public void setUp() {
		generator = new AccountGenerator();
	}
	
	@Test
	public void givenNoAccountsWhenGetAllAccountsEnsureCorrectResponse() throws Exception {
		mvc().perform(get("/accounts"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.accounts", hasSize(0)));
	}
	
	@Test
	public void givenOneAccountWhenGetAllAccountsEnsureCorrectResponse() throws Exception {
		List<Account> accounts = generator.listOf(1);
		doReturn(accounts).when(accountRepository).findAll();
		mvc().perform(get("/accounts"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.accounts", hasSize(1)))
			.andExpect(jsonPathLong("$.accounts[0].id", matchesId(accounts.get(0))))
			.andExpect(jsonPath("$.accounts[0].name", matchesName(accounts.get(0))));
	}
	
	private static class AccountGenerator extends DomainObjectGenerator<Account> {
		
		@Override
		protected Account createInstanceWithId(long id) {
			return Account.withIdAndName(id, "Account " + id);
		}
	}
	
	private static Matcher<Long> matchesId(Account account) {
		return equalTo(account.getId());
	}
	
	private static Matcher<String> matchesName(Account account) {
		return equalTo(account.getName());
	}

	@Test
	public void givenTwoAccountsWhenGetAllAccountsEnsureCorrectResponse() throws Exception {
		List<Account> accounts = generator.listOf(2);
		doReturn(accounts).when(accountRepository).findAll();
		
		mvc().perform(get("/accounts"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPathLong("$.accounts[0].id", matchesId(accounts.get(0))))
			.andExpect(jsonPath("$.accounts[0].name", matchesName(accounts.get(0))))
			.andExpect(jsonPathLong("$.accounts[1].id", matchesId(accounts.get(1))))
			.andExpect(jsonPath("$.accounts[1].name", matchesName(accounts.get(1))));
	}
	
	@Test
	public void givenExistingAccountWhenGetAccountsEnsureCorrectResponse() throws Exception {
		Account existingAccount = generator.createInstanceWithId(1);
		registerFindableAccount(existingAccount);
		
		mvc().perform(getAccountWithId(existingAccount.getId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPathLong("$.id", matchesId(existingAccount)))
			.andExpect(jsonPath("$.name", matchesName(existingAccount)));
	}
	
	private void registerFindableAccount(Account account) {
		doReturn(Optional.of(account)).when(accountRepository).findById(account.getId());
	}

	private MockHttpServletRequestBuilder getAccountWithId(long id) {
		return get("/accounts/{id}", id);
	}
	
	@Test
	public void givenNoExistingAccountsWhenGetAccountsEnsureCorrectResponse() throws Exception {
		long id = 1;
		ensureNoAccountForId(id);
		mvc().perform(getAccountWithId(id))
			.andExpect(status().isNotFound());
	}
	
	private void ensureNoAccountForId(long id) {
		doReturn(Optional.empty()).when(accountRepository).findById(id);
	}
	
	@Test
	public void givenNoExistingAccountsWithSuppliedIdWhenCreateAccountEnsureCorrectResponse() throws Exception {
		long id = 1;
		Account newAccount = Account.withName("Test name");
		setIdForInsertedAccount(id, newAccount);
		
		mvc().perform(postWithRequestBodyFrom(newAccount))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPathLong("$.id", equalTo(id)));
		
		assertAccountInserted();
	}

	private MockHttpServletRequestBuilder postWithRequestBodyFrom(Account newAccount) throws JsonProcessingException {
		return post("/accounts")
				.content(asStringWithoutNulls(newAccount))
				.contentType(MediaType.APPLICATION_JSON_UTF8);
	}
	
	private static String asStringWithoutNulls(Account account) throws JsonProcessingException {
		ObjectMapper ignoreNullsMapper = new ObjectMapper();
		ignoreNullsMapper.setSerializationInclusion(Include.NON_NULL);
		return ignoreNullsMapper.writeValueAsString(account);
	}

	private void setIdForInsertedAccount(long id, Account accountToCreate) {
		Account insertedAccount = Account.withIdAndName(id, accountToCreate.getName());
		doReturn(insertedAccount).when(accountRepository).insert(any(Account.class));
	}

	private void assertAccountInserted() {
		verify(accountRepository, times(1)).insert(any(Account.class));
	}
	
	@Test
	public void givenNoExistingAccountsWithSuppliedIdWhenCreateAccountWithoutNameEnsureCorrectResponse() throws Exception {
		long id = 1;
		Account newAccount = new Account(0, null);
		setIdForInsertedAccount(id, newAccount);
		
		mvc().perform(postWithRequestBodyFrom(newAccount))
			.andExpect(status().isBadRequest());
	}
}
