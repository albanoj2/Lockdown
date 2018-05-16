package com.lockdown.persist.store.util;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.lockdown.domain.DomainObject;
import com.lockdown.persist.store.DataStore;
import com.lockdown.persist.store.util.config.CascadingSaverConfig;
import com.lockdown.persist.store.util.data.cascade.domain.Daughter;
import com.lockdown.persist.store.util.data.cascade.domain.Grandchild;
import com.lockdown.persist.store.util.data.cascade.domain.Parent;
import com.lockdown.persist.store.util.data.cascade.domain.Son;
import com.lockdown.persist.store.util.data.cascade.store.DaughterDataStore;
import com.lockdown.persist.store.util.data.cascade.store.GrandchildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.ParentDataStore;
import com.lockdown.persist.store.util.data.cascade.store.SonDataStore;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CascadingSaverConfig.class)
public class CascadingSaverTest {

	@Autowired
	private ParentDataStore parentDataStore;
	
	@Autowired
	private SonDataStore sonDataStore;
	
	@Autowired
	private DaughterDataStore daughterDataStore;
	
	@Autowired
	private GrandchildDataStore grandchildDataStore;
	
	@Autowired
	private CascadingSaver saver;
	
	@Before
	public void setUp() {
		doAnswer(new SetIdAnswer<>()).when(parentDataStore).save(any(Parent.class));
		doAnswer(new SetIdAnswer<>()).when(sonDataStore).save(any(Son.class));
	}
	
	private static class SetIdAnswer<T extends DomainObject> implements Answer<T> {
	    
		@Override
		@SuppressWarnings("unchecked")
		public T answer(InvocationOnMock invocation) throws Throwable {
			Object[] args = invocation.getArguments();
			T object = (T) args[0];
			String newId = String.valueOf(Objects.hash(object.getId()));
			object.setId(newId);
			return object;
		}
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
		
		assertEquals(4, foundDataStores.size());
		assertEquals(parentDataStore, foundDataStores.get(Parent.class));
		assertEquals(sonDataStore, foundDataStores.get(Son.class));
		assertEquals(daughterDataStore, foundDataStores.get(Daughter.class));
		assertEquals(grandchildDataStore, foundDataStores.get(Grandchild.class));
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingChildlessParentThenEnsureOnlyParentIsSaved() {
		Parent parent = childlessParent();
		saver.saveAndCascade(parent);
		assertParentSaved(parent);
	}

	private static Parent childlessParent() {
		return new Parent("1", "Childless parent", new ArrayList<>(), new ArrayList<>());
	}
	
	private void assertParentSaved(Parent parent) {
		verify(parentDataStore, times(1)).save(eq(parent));
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithOneSonThenEnsureParentAndSonAreSaved() {
		Parent parent = parentWithOneSon();
		Son son = parent.getSons().get(0);
		saver.saveAndCascade(parent);
		assertParentSaved(parent);
		assertSonSaved(son);
	}
	
	private static Parent parentWithOneSon() {
		Son son = new Son("1", 234, "Single son", new ArrayList<>());
		return new Parent("1", "Parent with one son", List.of(son), new ArrayList<>());
	}
	
	private void assertSonSaved(Son brother) {
		verify(sonDataStore, times(1)).save(eq(brother));
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithOneSonThenEnsureSonReferenceUpdatedInParent() {
		Parent parent = parentWithOneSonWithNullId();
		saver.saveAndCascade(parent);
		Son updatedSon = parent.getSons().get(0);
		assertNotNull(updatedSon.getId());
	}
	
	private static Parent parentWithOneSonWithNullId() {
		Son son = new Son(null, 234, "Single son", new ArrayList<>());
		return new Parent("1", "Parent with one son", List.of(son), new ArrayList<>());
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoSonsThenEnsureParentAndSonsAreSaved() {
		Parent parent = parentWithTwoSons();
		Son son1 = parent.getSons().get(0);
		Son son2 = parent.getSons().get(1);
		saver.saveAndCascade(parent);
		assertParentSaved(parent);
		assertSonSaved(son1);
		assertSonSaved(son2);
	}
	
	private static Parent parentWithTwoSons() {
		Son son1 = new Son("1", 234, "Son 1", new ArrayList<>());
		Son son2 = new Son("2", 234, "Son 2", new ArrayList<>());
		return new Parent("1", "Parent with one son", List.of(son1, son2), new ArrayList<>());
	}
	
	@Test
	public void givenValidDataStoreRegisteredWhenSavingParentWithTwoSonsThenEnsureSonReferencesUpdatedInParent() {
		Parent parent = parentWithTwoSonsWithNullIds();
		saver.saveAndCascade(parent);
		Son updatedSon1 = parent.getSons().get(0);
		Son updatedSon2 = parent.getSons().get(1);
		assertNotNull(updatedSon1.getId());
		assertNotNull(updatedSon2.getId());
	}
	
	private static Parent parentWithTwoSonsWithNullIds() {
		Son son1 = new Son(null, 234, "Son 1", new ArrayList<>());
		Son son2 = new Son(null, 234, "Son 2", new ArrayList<>());
		return new Parent("1", "Parent with one son", List.of(son1, son2), new ArrayList<>());
	}
}
