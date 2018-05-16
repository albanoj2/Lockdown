package com.lockdown.persist.store.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Map;

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
import com.lockdown.persist.store.util.data.cascade.domain.Brother;
import com.lockdown.persist.store.util.data.cascade.domain.Grandchild;
import com.lockdown.persist.store.util.data.cascade.domain.Parent;
import com.lockdown.persist.store.util.data.cascade.domain.Sister;
import com.lockdown.persist.store.util.data.cascade.store.BrotherDataStore;
import com.lockdown.persist.store.util.data.cascade.store.GrandchildDataStore;
import com.lockdown.persist.store.util.data.cascade.store.ParentDataStore;
import com.lockdown.persist.store.util.data.cascade.store.SisterDataStore;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CascadingSaverConfig.class)
public class CascadingSaverTest {

	@Autowired
	private ParentDataStore parentDataStore;
	
	@Autowired
	private BrotherDataStore brotherDataStore;
	
	@Autowired
	private SisterDataStore sisterDataStore;
	
	@Autowired
	private GrandchildDataStore grandchildDataStore;
	
	@Autowired
	private CascadingSaver saver;
	
	@Before
	public void setUp() {
		initializeParentDataStore();
	}
	
	private void initializeParentDataStore() {
		doAnswer(new SetIdAnswer<>("1")).when(parentDataStore).save(any(Parent.class));
	}
	
	private static class SetIdAnswer<T extends DomainObject> implements Answer<T> {
		
		private final String id;
		
		public SetIdAnswer(String id) {
			this.id = id;
		}
	    
		@Override
		@SuppressWarnings("unchecked")
		public T answer(InvocationOnMock invocation) throws Throwable {
			Object[] args = invocation.getArguments();
			T object = (T) args[0];
			object.setId(id);
			return object;
		}
	  }
	
	@Test(expected = NullPointerException.class)
	public void whenSaveAndCascadeNullThenThrowNullPointerException() {
		saver.saveAndCascade(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
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
		assertEquals(brotherDataStore, foundDataStores.get(Brother.class));
		assertEquals(sisterDataStore, foundDataStores.get(Sister.class));
		assertEquals(grandchildDataStore, foundDataStores.get(Grandchild.class));
	}
	
//	@Test
//	public void givenValidDataStoreRegisteredWhenSavingChildlessParentThenEnsureOnlyParentIsSaved() {
//		Parent parent = childlessParent();
//		saver.saveAndCascade(parent);
//		
//	}
//
//	private static Parent childlessParent() {
//		return new Parent("1", "Childless parent", new ArrayList<>(), new ArrayList<>());
//	}
//	
//	private void assertParentSaved(Parent parent) {
//		verify(parentDataStore, times(1)).save(eq(parent));
//	}
}
