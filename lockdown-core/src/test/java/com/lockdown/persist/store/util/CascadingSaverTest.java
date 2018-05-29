package com.lockdown.persist.store.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.lockdown.domain.Identifiable;
import com.lockdown.persist.store.DataStore;
import com.lockdown.persist.store.util.config.CascadingSaverConfig;
import com.lockdown.persist.store.util.data.cascade.domain.Child;
import com.lockdown.persist.store.util.data.cascade.domain.DomainSubclass;
import com.lockdown.persist.store.util.data.cascade.domain.Grandchild;
import com.lockdown.persist.store.util.data.cascade.domain.Parent;
import com.lockdown.persist.store.util.data.cascade.store.ChildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.DomainSubclassDataStore;
import com.lockdown.persist.store.util.data.cascade.store.GrandchildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.ParentDataStore;
import com.lockdown.persist.store.util.helper.AssertChildUpdatedBeforeSavingParentListener;
import com.lockdown.persist.store.util.helper.AssertGrandchildUpdatedBeforeSavingChildListener;
import com.lockdown.persist.store.util.helper.DataStoresWatcher;
import com.lockdown.persist.store.util.helper.GrandchildrenBeforeChildrenBeforeParentAssertionStrategy;
import com.lockdown.persist.store.util.helper.InOrderAssertionStrategy;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CascadingSaverConfig.class)
public class CascadingSaverTest {

	@Autowired
	private ParentDataStore parentDataStore;
	
	@Autowired
	private ChildDataStore childDataStore;
	
	@Autowired
	private GrandchildDataStore grandchildDataStore;
	
	@Autowired
	private DomainSubclassDataStore domainSubclassDataStore;
	
	@Autowired
	private DataStoresWatcher dataStoresWatcher;
	
	@Autowired
	private CascadingSaver saver;
	
	@Before
	public void setUp() {
		dataStoresWatcher.clearSavedObjects();
		dataStoresWatcher.clearListeners();
	}
	
	@Test(expected = NullPointerException.class)
	public void whenSaveAndCascadeNullThenThrowNullPointerException() {
		saver.saveAndCascade(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void givenNoDataStoreForSuppliedDomainObjectWhenSaveAndCascadeThenThrowIllegalArgumentException() {
		saver.saveAndCascade(domainObjectWithoutDataStore());
	}
	
	private static Identifiable domainObjectWithoutDataStore() {
		return Mockito.mock(Identifiable.class);
	}
	
	@Test
	public void givenFourValidDataStoresRegisteredWhenInitializingThenHaveValidDataStoresFound() {
		Map<Class<? extends Identifiable>, DataStore<? extends Identifiable>> foundDataStores = saver.getFoundDataStores();
		
		assertEquals(4, foundDataStores.size());
		assertEquals(parentDataStore, foundDataStores.get(Parent.class));
		assertEquals(childDataStore, foundDataStores.get(Child.class));
		assertEquals(grandchildDataStore, foundDataStores.get(Grandchild.class));
		assertEquals(domainSubclassDataStore, foundDataStores.get(DomainSubclass.class));
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingChildlessParentThenEnsureOnlyParentIsSaved() {
		Parent parent = generateChildlessParent();
		saver.saveAndCascade(parent);
		dataStoresWatcher.assertOrder(new GrandchildrenBeforeChildrenBeforeParentAssertionStrategy());
	}
	
	private static Parent generateChildlessParent() {
		return generateParent(new HashSet<>(), new ArrayList<>());
	}

	private static Parent generateParent(Set<Child> firstSubtree, List<Child> secondSubtree) {
		return new Parent(null, "Some parent", firstSubtree, secondSubtree, List.of("foo", "bar"));
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithOneFirstSubtreeChildThenEnsureParentAndChildAreSavedInOrder() {
		Parent parent = parentWithFirstSubtreeChild();
		saver.saveAndCascade(parent);
		dataStoresWatcher.assertOrder(new GrandchildrenBeforeChildrenBeforeParentAssertionStrategy());
	}
	
	private static Parent parentWithFirstSubtreeChild() {
		Child child = generateChildWithoutGrandchildren();
		return generateParent(Set.of(child), new ArrayList<>());
	}
	
	private static Child generateChildWithoutGrandchildren() {
		return generateChild(new ArrayList<>());
	}
	
	private static Child generateChild(List<Grandchild> grandchildren) {
		return new Child(null, 0.27, 234, "Some child", grandchildren);
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithOneFirstSubtreeChildThenEnsureChildrenUpdatedBeforeSavingparent() {
		Parent parent = parentWithFirstSubtreeChild();
		dataStoresWatcher.registerOnSaveListener(new AssertChildUpdatedBeforeSavingParentListener());
		saver.saveAndCascade(parent);
	}

	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoFirstSubtreeChildrenThenEnsureParentAndChildrenAreSavedInOrder() {
		Parent parent = parentWithTwoFirstSubtreeChildren();
		saver.saveAndCascade(parent);
		dataStoresWatcher.assertOrder(new GrandchildrenBeforeChildrenBeforeParentAssertionStrategy());
	}
	
	private static Parent parentWithTwoFirstSubtreeChildren() {
		Child child1 = generateChildWithoutGrandchildren();
		Child child2 = generateChildWithoutGrandchildren();
		return generateParent(Set.of(child1, child2), new ArrayList<>());
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoFirstSubtreeChildrenThenEnsureChildrenUpdatedBeforeSavingParent() {
		Parent parent = parentWithTwoFirstSubtreeChildren();
		dataStoresWatcher.registerOnSaveListener(new AssertChildUpdatedBeforeSavingParentListener());
		saver.saveAndCascade(parent);
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoChildrenInFirstSubtreeAndOneChildInSecondSubtreeThenEnsureParentAndChildrenAreSavedInOrder() {
		Parent parent = parentWithTwoFirstSubtreeChildrenAndOneSecondSubtreeChild();
		saver.saveAndCascade(parent);
		dataStoresWatcher.assertOrder(new GrandchildrenBeforeChildrenBeforeParentAssertionStrategy());
	}
	
	private static Parent parentWithTwoFirstSubtreeChildrenAndOneSecondSubtreeChild() {
		Child firstSubtreeChild1 = generateChildWithoutGrandchildren();
		Child firstSubtreeChild2 = generateChildWithoutGrandchildren();
		Child secondSubtreeChild = generateChildWithoutGrandchildren();
		return generateParent(Set.of(firstSubtreeChild1, firstSubtreeChild2), List.of(secondSubtreeChild));
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoFirstSubtreeChildrenAndOneSecondSubtreeChildThenEnsureChildrenUpdatedBeforeSavingParent() {
		Parent parent = parentWithTwoFirstSubtreeChildrenAndOneSecondSubtreeChild();
		dataStoresWatcher.registerOnSaveListener(new AssertChildUpdatedBeforeSavingParentListener());
		saver.saveAndCascade(parent);
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoChildrenInEachSubtreeThenEnsureParentAndChildrenAreSavedInOrder() {
		Parent parent = parentWithTwoChildrenInEachSubtree();
		saver.saveAndCascade(parent);
		dataStoresWatcher.assertOrder(new GrandchildrenBeforeChildrenBeforeParentAssertionStrategy());
	}
	
	private static Parent parentWithTwoChildrenInEachSubtree() {
		Child firstSubtreeChild1 = generateChildWithoutGrandchildren();
		Child firstSubtreeChild2 = generateChildWithoutGrandchildren();
		Child secondSubtreeChild1 = generateChildWithoutGrandchildren();
		Child secondSubtreeChild2 = generateChildWithoutGrandchildren();
		return generateParent(Set.of(firstSubtreeChild1, firstSubtreeChild2), List.of(secondSubtreeChild1, secondSubtreeChild2));
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoChildrenInEachSubtreeThenEnsureChildrenUpdatedBeforeSavingParent() {
		Parent parent = parentWithTwoChildrenInEachSubtree();
		dataStoresWatcher.registerOnSaveListener(new AssertChildUpdatedBeforeSavingParentListener());
		saver.saveAndCascade(parent);
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoChildrenInEachSubtreeAndTwoGrandchildrenInEachChildThenEnsureParentAndChildrenAreSavedInOrder() {
		Parent parent = parentWithTwoChildrenInEachSubtreeAndTwoGrandchildrenInEachChild();
		saver.saveAndCascade(parent);
		dataStoresWatcher.assertOrder(new GrandchildrenBeforeChildrenBeforeParentAssertionStrategy());
	}
	
	private static Parent parentWithTwoChildrenInEachSubtreeAndTwoGrandchildrenInEachChild() {
		Child firstSubtreeChild1 = generateChild(List.of(generateGrandchild(), generateGrandchild()));
		Child firstSubtreeChild2 = generateChild(List.of(generateGrandchild(), generateGrandchild()));
		Child secondSubtreeChild1 = generateChild(List.of(generateGrandchild(), generateGrandchild()));
		Child secondSubtreeChild2 = generateChild(List.of(generateGrandchild(), generateGrandchild()));
		return generateParent(Set.of(firstSubtreeChild1, firstSubtreeChild2), List.of(secondSubtreeChild1, secondSubtreeChild2));
	}
	
	private static Grandchild generateGrandchild() {
		return new Grandchild(null, true);
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoChildrenInEachSubtreeAndTwoGrandchildrenInEachChildThenEnsureGrandchildrenUpdatedBeforeChildrenSavedAndChildrenUpdatedBeforeParentSaved() {
		Parent parent = parentWithTwoChildrenInEachSubtreeAndTwoGrandchildrenInEachChild();
		dataStoresWatcher.registerOnSaveListener(new AssertGrandchildUpdatedBeforeSavingChildListener());
		dataStoresWatcher.registerOnSaveListener(new AssertChildUpdatedBeforeSavingParentListener());
		saver.saveAndCascade(parent);
	}
	
	@Test
	public void givenDomainObjectHasNestedDomainObjectInSuperclassWhenSaveAndCascadeThenEnsureSuperclassDomainObjectIsSavedFirst() {
		DomainSubclass subclass = new DomainSubclass(null, generateChildlessParent(), generateChildWithoutGrandchildren());
		DomainSubclass savedSubclass = saver.saveAndCascade(subclass);
		
		dataStoresWatcher.assertOrder(InOrderAssertionStrategy
			.first(subclass.getChild(), subclass.getParent())
			.thenExpect(savedSubclass)
		);
	}
}
