package com.lockdown.persist.store.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.lockdown.domain.DomainObject;
import com.lockdown.persist.store.DataStore;
import com.lockdown.persist.store.util.config.CascadingSaverConfig;
import com.lockdown.persist.store.util.data.cascade.domain.Child;
import com.lockdown.persist.store.util.data.cascade.domain.Grandchild;
import com.lockdown.persist.store.util.data.cascade.domain.Parent;
import com.lockdown.persist.store.util.data.cascade.store.ChildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.GrandchildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.ParentDataStore;

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
	private CascadingSaver saver;
	
	private InOrder saveOrdering;
	
	@Before
	public void setUp() {
		Mockito.reset(parentDataStore);
		Mockito.reset(childDataStore);
		Mockito.reset(grandchildDataStore);
		doAnswer(SaveAnswers.assignId()).when(parentDataStore).save(any(Parent.class));
		doAnswer(SaveAnswers.assignId()).when(childDataStore).save(any(Child.class));
		doAnswer(SaveAnswers.assignId()).when(grandchildDataStore).save(any(Grandchild.class));
	}
	
	@Test(expected = NullPointerException.class)
	public void whenSaveAndCascadeNullThenThrowNullPointerException() {
		saver.saveAndCascade(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void givenNoDataStoreForSuppliedDomainObjectWhenSaveAndCascadeThenThrowIllegalArgumentException() {
		saver.saveAndCascade(domainObjectWithoutDataStore());
	}
	
	private static DomainObject domainObjectWithoutDataStore() {
		return Mockito.mock(DomainObject.class);
	}
	
	@Test
	public void givenFourValidDataStoresRegisteredWhenInitializingThenHaveValidDataStoresFound() {
		Map<Class<? extends DomainObject>, DataStore<? extends DomainObject>> foundDataStores = saver.getFoundDataStores();
		
		assertEquals(3, foundDataStores.size());
		assertEquals(parentDataStore, foundDataStores.get(Parent.class));
		assertEquals(childDataStore, foundDataStores.get(Child.class));
		assertEquals(grandchildDataStore, foundDataStores.get(Grandchild.class));
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingChildlessParentThenEnsureOnlyParentIsSaved() {
		Parent parent = generateChildlessParent();
		saver.saveAndCascade(parent);
		verifySavedInOrder()
			.assertSavedNext(parent);
	}
	
	private static Parent generateChildlessParent() {
		return generateParent(new ArrayList<>(), new ArrayList<>());
	}

	private static Parent generateParent(List<Child> firstSubtree, List<Child> secondSubtree) {
		return new Parent(null, "Some parent", firstSubtree, secondSubtree);
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithOneFirstSubtreeChildThenEnsureParentAndChildAreSavedInOrder() {
		Parent parent = parentWithFirstSubtreeChild();
		Child child = parent.getFirstSubtree().get(0);
		saver.saveAndCascade(parent);
		verifySavedInOrder()
			.assertSavedNext(child)
			.assertSavedNext(parent);
	}
	
	private static Parent parentWithFirstSubtreeChild() {
		Child child = generateChildWithoutGrandchildren();
		return generateParent(List.of(child), new ArrayList<>());
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
		assertChildrenUpdatedBeforeSavingParentAfterSaveCompletes();
		saver.saveAndCascade(parent);
	}

	private void assertChildrenUpdatedBeforeSavingParentAfterSaveCompletes() {
		doAnswer(SaveAnswers.ensureChildrenUpdatedBeforeSavingParent()).when(parentDataStore).save(any(Parent.class));
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoFirstSubtreeChildrenThenEnsureParentAndChildrenAreSavedInOrder() {
		Parent parent = parentWithTwoFirstSubtreeChildren();
		Child child1 = parent.getFirstSubtree().get(0);
		Child child2 = parent.getFirstSubtree().get(1);
		saver.saveAndCascade(parent);
		
		verifySavedInOrder()
			.assertSavedNext(child1)
			.assertSavedNext(child2)
			.assertSavedNext(parent);
	}
	
	private static Parent parentWithTwoFirstSubtreeChildren() {
		Child child1 = generateChildWithoutGrandchildren();
		Child child2 = generateChildWithoutGrandchildren();
		return generateParent(List.of(child1, child2), new ArrayList<>());
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoFirstSubtreeChildrenThenEnsureChildrenUpdatedBeforeSavingParent() {
		Parent parent = parentWithTwoFirstSubtreeChildren();
		assertChildrenUpdatedBeforeSavingParentAfterSaveCompletes();
		saver.saveAndCascade(parent);
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoChildrenInFirstSubtreeAndOneChildInSecondSubtreeThenEnsureParentAndChildrenAreSavedInOrder() {
		Parent parent = parentWithTwoFirstSubtreeChildrenAndOneSecondSubtreeChild();
		Child firstSubtreeChild1 = parent.getFirstSubtree().get(0);
		Child firstSubtreeChild2 = parent.getFirstSubtree().get(1);
		Child secondSubtreeChild1 = parent.getSecondSubtree().get(0);
		saver.saveAndCascade(parent);
		
		verifySavedInOrder()
			.assertSavedNext(secondSubtreeChild1)
			.assertSavedNext(firstSubtreeChild1)
			.assertSavedNext(firstSubtreeChild2)
			.assertSavedNext(parent);
	}
	
	private static Parent parentWithTwoFirstSubtreeChildrenAndOneSecondSubtreeChild() {
		Child firstSubtreeChild1 = generateChildWithoutGrandchildren();
		Child firstSubtreeChild2 = generateChildWithoutGrandchildren();
		Child secondSubtreeChild = generateChildWithoutGrandchildren();
		return generateParent(List.of(firstSubtreeChild1, firstSubtreeChild2), List.of(secondSubtreeChild));
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoFirstSubtreeChildrenAndOneSecondSubtreeChildThenEnsureChildrenUpdatedBeforeSavingParent() {
		Parent parent = parentWithTwoFirstSubtreeChildrenAndOneSecondSubtreeChild();
		assertChildrenUpdatedBeforeSavingParentAfterSaveCompletes();
		saver.saveAndCascade(parent);
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoChildrenInEachSubtreeThenEnsureParentAndChildrenAreSavedInOrder() {
		Parent parent = parentWithTwoChildrenInEachSubtree();
		Child firstSubtreeChild1 = parent.getFirstSubtree().get(0);
		Child firstSubtreeChild2 = parent.getFirstSubtree().get(1);
		Child secondSubtreeChild1 = parent.getSecondSubtree().get(0);
		Child secondSubtreeChild2 = parent.getSecondSubtree().get(1);
		saver.saveAndCascade(parent);
		
		verifySavedInOrder()
			.assertSavedNext(secondSubtreeChild1)
			.assertSavedNext(secondSubtreeChild2)
			.assertSavedNext(firstSubtreeChild1)
			.assertSavedNext(firstSubtreeChild2)
			.assertSavedNext(parent);
	}
	
	private static Parent parentWithTwoChildrenInEachSubtree() {
		Child firstSubtreeChild1 = generateChildWithoutGrandchildren();
		Child firstSubtreeChild2 = generateChildWithoutGrandchildren();
		Child secondSubtreeChild1 = generateChildWithoutGrandchildren();
		Child secondSubtreeChild2 = generateChildWithoutGrandchildren();
		return generateParent(List.of(firstSubtreeChild1, firstSubtreeChild2), List.of(secondSubtreeChild1, secondSubtreeChild2));
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoChildrenInEachSubtreeThenEnsureChildrenUpdatedBeforeSavingParent() {
		Parent parent = parentWithTwoChildrenInEachSubtree();
		assertChildrenUpdatedBeforeSavingParentAfterSaveCompletes();
		saver.saveAndCascade(parent);
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoChildrenInEachSubtreeAndTwoGrandchildrenInEachChildThenEnsureParentAndChildrenAreSavedInOrder() {
		SaveWatcher watcher = new SaveWatcher();
		registerSaveWatcher(watcher);
		Parent parent = parentWithTwoChildrenInEachSubtreeAndTwoGrandchildrenInEachChild();
		
		Parent savedParent = saver.saveAndCascade(parent);
		
		Grandchild firstSubtreeChild1Grandchild1 = parent.getFirstSubtree().get(0).getChildren().get(0);
		Grandchild firstSubtreeChild1Grandchild2 = parent.getFirstSubtree().get(0).getChildren().get(1);
		Child firstSubtreeChild1 = parent.getFirstSubtree().get(0);
		
		Grandchild firstSubtreeChild2Grandchild1 = parent.getFirstSubtree().get(1).getChildren().get(0);
		Grandchild firstSubtreeChild2Grandchild2 = parent.getFirstSubtree().get(1).getChildren().get(1);
		Child firstSubtreeChild2 = parent.getFirstSubtree().get(1);

		Grandchild secondSubtreeChild1Grandchild1 = parent.getSecondSubtree().get(0).getChildren().get(0);
		Grandchild secondSubtreeChild1Grandchild2 = parent.getSecondSubtree().get(0).getChildren().get(1);
		Child secondSubtreeChild1 = parent.getSecondSubtree().get(0);
		
		Grandchild secondSubtreeChild2Grandchild1 = parent.getSecondSubtree().get(1).getChildren().get(0);
		Grandchild secondSubtreeChild2Grandchild2 = parent.getSecondSubtree().get(1).getChildren().get(1);
		Child secondSubtreeChild2 = parent.getSecondSubtree().get(1);
		
		watcher
			.expectSavedNext(secondSubtreeChild1Grandchild1)
			.expectSavedNext(secondSubtreeChild1Grandchild2)
			.expectSavedNext(secondSubtreeChild1)
			.expectSavedNext(secondSubtreeChild2Grandchild1)
			.expectSavedNext(secondSubtreeChild2Grandchild2)
			.expectSavedNext(secondSubtreeChild2)
			.expectSavedNext(firstSubtreeChild1Grandchild1)
			.expectSavedNext(firstSubtreeChild1Grandchild2)
			.expectSavedNext(firstSubtreeChild1)
			.expectSavedNext(firstSubtreeChild2Grandchild1)
			.expectSavedNext(firstSubtreeChild2Grandchild2)
			.expectSavedNext(firstSubtreeChild2)
			.expectSavedNext(savedParent)
			.verify();
		
//		verifySavedInOrder()
//			.assertSavedNext(firstSubtreeChild1Grandchild1)
//			.assertSavedNext(firstSubtreeChild1Grandchild2)
//			.assertSavedNext(firstSubtreeChild1)
//			.assertSavedNext(firstSubtreeChild2Grandchild1)
//			.assertSavedNext(firstSubtreeChild2Grandchild2)
//			.assertSavedNext(firstSubtreeChild2)
//			.assertSavedNext(secondSubtreeChild1Grandchild1)
//			.assertSavedNext(secondSubtreeChild1Grandchild2)
//			.assertSavedNext(secondSubtreeChild1)
//			.assertSavedNext(secondSubtreeChild2Grandchild1)
//			.assertSavedNext(secondSubtreeChild2Grandchild2)
//			.assertSavedNext(secondSubtreeChild2)
//			.assertSavedNext(parent);
	}
	
	private void registerSaveWatcher(SaveWatcher watcher) {
		doAnswer(watcher).when(parentDataStore).save(any(Parent.class));
		doAnswer(watcher).when(childDataStore).save(any(Child.class));
		doAnswer(watcher).when(grandchildDataStore).save(any(Grandchild.class));
	}
	
	private static Parent parentWithTwoChildrenInEachSubtreeAndTwoGrandchildrenInEachChild() {
		
		Grandchild firstSubtreeChild1Grandchild1 = generateGrandchild();
		Grandchild firstSubtreeChild1Grandchild2 = generateGrandchild();
		Child firstSubtreeChild1 = generateChild(List.of(firstSubtreeChild1Grandchild1, firstSubtreeChild1Grandchild2));
		
		Grandchild firstSubtreeChild2Grandchild1 = generateGrandchild();
		Grandchild firstSubtreeChild2Grandchild2 = generateGrandchild();
		Child firstSubtreeChild2 = generateChild(List.of(firstSubtreeChild2Grandchild1, firstSubtreeChild2Grandchild2));
		
		Grandchild secondSubtreeChild1Grandchild1 = generateGrandchild();
		Grandchild secondSubtreeChild1Grandchild2 = generateGrandchild();
		Child secondSubtreeChild1 = generateChild(List.of(secondSubtreeChild1Grandchild1, secondSubtreeChild1Grandchild2));
		
		Grandchild secondSubtreeChild2Grandchild1 = generateGrandchild();
		Grandchild secondSubtreeChild2Grandchild2 = generateGrandchild();
		Child secondSubtreeChild2 = generateChild(List.of(secondSubtreeChild2Grandchild1, secondSubtreeChild2Grandchild2));
		
		return generateParent(List.of(firstSubtreeChild1, firstSubtreeChild2), List.of(secondSubtreeChild1, secondSubtreeChild2));
	}
	
	private static Grandchild generateGrandchild() {
		return new Grandchild(null, true);
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoChildrenInEachSubtreeAndTwoGrandchildrenInEachChildThenEnsureGrandchildrenUpdatedBeforeChildrenSavedAndChildrenUpdatedBeforeParentSaved() {
		Parent parent = parentWithTwoChildrenInEachSubtreeAndTwoGrandchildrenInEachChild();
		assertGrandchildrenUpdatedBeforeSavingChildrenAfterSaveCompletes();
		assertChildrenUpdatedBeforeSavingParentAfterSaveCompletes();
		saver.saveAndCascade(parent);
	}
	
	private void assertGrandchildrenUpdatedBeforeSavingChildrenAfterSaveCompletes() {
		doAnswer(SaveAnswers.ensureGrandchildrenUpdatedBeforeSavingChild()).when(childDataStore).save(any(Child.class));
	}
	
	private SaveOrderVerifier verifySavedInOrder() {
		return new SaveOrderVerifier(saveOrdering, parentDataStore, childDataStore, grandchildDataStore);
	}
}
