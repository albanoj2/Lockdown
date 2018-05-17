package com.lockdown.persist.store.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;

public class SaveWatcher implements Answer<MockDomainObject> {
	
	private final List<MockDomainObject> actuallySaved = new ArrayList<>();
	private final List<MockDomainObject> expected = new ArrayList<>();

	@Override
	public MockDomainObject answer(InvocationOnMock invocation) throws Throwable {		
		MockDomainObject savedMock = SaveAnswers.assignId().answer(invocation);
		actuallySaved.add(savedMock);
		return savedMock;
	}
	
	public SaveWatcher expectSavedNext(MockDomainObject mock) {
		expected.add(mock);
		return this;
	}
	
	public void verify() {
		assertTrue(actuallySaved.equals(expected));
	}
}
