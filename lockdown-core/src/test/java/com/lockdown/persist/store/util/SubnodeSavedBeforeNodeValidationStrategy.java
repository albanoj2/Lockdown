package com.lockdown.persist.store.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lockdown.persist.store.util.data.cascade.domain.Child;
import com.lockdown.persist.store.util.data.cascade.domain.Grandchild;
import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;
import com.lockdown.persist.store.util.data.cascade.domain.Parent;

public class SubnodeSavedBeforeNodeValidationStrategy implements OrderValidationStrategy {

	@Override
	public void validateOrder(List<MockDomainObject> objects) {

		List<Grandchild> thisSubtreeGrandchildren = new ArrayList<>();
		List<Child> foundChildren = new ArrayList<>();

		for (MockDomainObject savedObject : objects) {

			if (savedObject instanceof Grandchild) {
				// Leaf nodes
				thisSubtreeGrandchildren.add((Grandchild) savedObject);
			} 
			else if (savedObject instanceof Child) {
				// Interior nodes
				Child savedChild = (Child) savedObject;
				assertListsEqualIgnoreOrder(savedChild.getChildren(), thisSubtreeGrandchildren);
				thisSubtreeGrandchildren.clear();
				foundChildren.add(savedChild);
			} 
			else if (savedObject instanceof Parent) {
				// Root node
				Parent savedParent = (Parent) savedObject;
				List<Child> expectedChild = combineSubtrees(savedParent);
				assertListsEqualIgnoreOrder(expectedChild, foundChildren);
				assertLastElementInList(objects, savedParent);
			}
		}
	}

	private void assertListsEqualIgnoreOrder(List<?> expectedSubtree, List<?> actualSubtree) {
		assertEquals(expectedSubtree.size(), actualSubtree.size());
		assertTrue(actualSubtree.containsAll(expectedSubtree));
	}

	private static List<Child> combineSubtrees(Parent parent) {
		return Stream.concat(parent.getFirstSubtree().stream(), parent.getSecondSubtree().stream())
				.collect(Collectors.toList());
	}

	private static <T> void assertLastElementInList(List<T> list, T element) {
		assertEquals(list.indexOf(element), list.size() - 1);
	}
}
