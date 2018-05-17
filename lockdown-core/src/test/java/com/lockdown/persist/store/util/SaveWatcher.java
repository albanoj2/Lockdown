package com.lockdown.persist.store.util;

import java.util.ArrayList;
import java.util.List;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;

public class SaveWatcher implements Answer<MockDomainObject> {
	
	private final List<MockDomainObject> actuallySaved = new ArrayList<>();

	@Override
	public MockDomainObject answer(InvocationOnMock invocation) throws Throwable {		
		MockDomainObject savedMock = SaveAnswers.assignId().answer(invocation);
		actuallySaved.add(savedMock);
		return savedMock;
	}
	
	public void validateOrder(OrderValidationStrategy strategy) {
		strategy.validateOrder(actuallySaved);
	}
}
