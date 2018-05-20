package com.lockdown.persist.store.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.lockdown.persist.store.util.config.InvalidCascadingSaverConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = InvalidCascadingSaverConfig.class)
public class CascadingSaverWithInvalidDataStoreTest {
	
	@Autowired
	private ListableBeanFactory beanFactory;

	@Test(expected = IllegalStateException.class)
	public void saveAndCascadeWithObjectAnnotatedAsADataStoreButDoesNotImplementDataStoreInterfaceEnsureIllegalStateExceptionThrown() throws Throwable {
		CascadingSaver saver = new CascadingSaver(beanFactory);
		saver.retrieveDataStores();
	}
}
