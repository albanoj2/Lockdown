package com.lockdown.persist.store.util;

import static org.junit.Assert.assertNotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.lockdown.domain.DomainObject;
import com.lockdown.persist.store.util.data.cascade.domain.Child;
import com.lockdown.persist.store.util.data.cascade.domain.Grandchild;
import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;
import com.lockdown.persist.store.util.data.cascade.domain.Parent;

class SaveAnswers {
	
	private static AtomicLong nextId = new AtomicLong();
	
	public static Answer<MockDomainObject> assignId() {
		return (InvocationOnMock invocation) -> {
			Object[] args = invocation.getArguments();
			MockDomainObject mock = (MockDomainObject) args[0];
			MockDomainObject copy = mock.copy();
			copy.setId(String.valueOf(nextId.getAndIncrement()));
			return copy;
		};
	}

	public static ParentSaveAnswer assignParentId() {
		return new ParentSaveAnswer();
	}

	public static class ParentSaveAnswer implements Answer<Parent> {

		@Override
		public Parent answer(InvocationOnMock invocation) throws Throwable {
			Object[] args = invocation.getArguments();
			Parent parent = (Parent) args[0];
			Parent parentCopy = parent.copy();
			parentCopy.setId(assignId(parentCopy));
			return parentCopy;
		}
	}
	
	private static String assignId(DomainObject object) {
		return String.valueOf(Objects.hash(object.getId()));
	}
	
	public static ChildSaveAnswer assignChildId() {
		return new ChildSaveAnswer();
	}

	public static class ChildSaveAnswer implements Answer<Child> {

		@Override
		public Child answer(InvocationOnMock invocation) throws Throwable {
			Object[] args = invocation.getArguments();
			Child child = (Child) args[0];
			Child childCopy = child.copy();
			childCopy.setId(assignId(childCopy));
			return childCopy;
		}
	}
	
	public static GrandchildSaveAnswer assignGrandchildId() {
		return new GrandchildSaveAnswer();
	}

	public static class GrandchildSaveAnswer implements Answer<Grandchild> {

		@Override
		public Grandchild answer(InvocationOnMock invocation) throws Throwable {
			Object[] args = invocation.getArguments();
			Grandchild grandchild = (Grandchild) args[0];
			Grandchild grandchildCopy = grandchild.copy();
			grandchildCopy.setId(assignId(grandchildCopy));
			return grandchildCopy;
		}
	}
	
	public static Answer<Parent> ensureChildrenUpdatedBeforeSavingParent() {
		return (InvocationOnMock invocation) -> {
			Object[] args = invocation.getArguments();
			Parent savedParent = (Parent) args[0];
			
			for (Child child : savedParent.getFirstSubtree()) {
				assertNotNull(child.getId());
			}

			for (Child child : savedParent.getSecondSubtree()) {
				assertNotNull(child.getId());
			}

			return SaveAnswers.assignParentId().answer(invocation);
		};
	}
	
	public static Answer<Child> ensureGrandchildrenUpdatedBeforeSavingChild() {
		return (InvocationOnMock invocation) -> {
			Object[] args = invocation.getArguments();
			Child savedChild = (Child) args[0];

			for (Grandchild grandchild : savedChild.getChildren()) {
				assertNotNull(grandchild.getId());
			}

			return assignChildId().answer(invocation);
		};
	}
}
