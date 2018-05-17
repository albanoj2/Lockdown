package com.lockdown.persist.store.util;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.atomic.AtomicLong;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.lockdown.persist.store.util.data.cascade.domain.Child;
import com.lockdown.persist.store.util.data.cascade.domain.Grandchild;
import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;
import com.lockdown.persist.store.util.data.cascade.domain.Parent;

class SaveAnswers {
	
	private static AtomicLong nextId = new AtomicLong();
	
	public static Answer<MockDomainObject> assignId() {
		return (InvocationOnMock invocation) -> {
			MockDomainObject mock = (MockDomainObject) invocation.getArguments()[0];
			MockDomainObject copy = mock.copy();
			copy.setId(String.valueOf(nextId.getAndIncrement()));
			return copy;
		};
	}

	public static Answer<MockDomainObject> ensureChildrenUpdatedBeforeSavingParent() {
		return (InvocationOnMock invocation) -> {
			Parent savedParent = (Parent) invocation.getArguments()[0];
			
			for (Child child : savedParent.getFirstSubtree()) {
				assertNotNull(child.getId());
			}

			for (Child child : savedParent.getSecondSubtree()) {
				assertNotNull(child.getId());
			}

			return assignId().answer(invocation);
		};
	}
	
	public static Answer<MockDomainObject> ensureGrandchildrenUpdatedBeforeSavingChild() {
		return (InvocationOnMock invocation) -> {
			Child savedChild = (Child) invocation.getArguments()[0];

			for (Grandchild grandchild : savedChild.getChildren()) {
				assertNotNull(grandchild.getId());
			}

			return assignId().answer(invocation);
		};
	}
}
