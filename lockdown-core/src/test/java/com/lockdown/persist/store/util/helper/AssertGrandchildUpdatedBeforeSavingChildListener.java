package com.lockdown.persist.store.util.helper;

import static org.junit.Assert.assertNotNull;

import com.lockdown.persist.store.util.data.cascade.domain.Child;
import com.lockdown.persist.store.util.data.cascade.domain.Grandchild;
import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;

public class AssertGrandchildUpdatedBeforeSavingChildListener implements OnSaveListener {

	@Override
	public void onSave(MockDomainObject object) {
		
		if (object instanceof Child) {
			Child savedChild = (Child) object;
	
			for (Grandchild grandchild : savedChild.getChildren()) {
				assertNotNull(grandchild.getId());
			}
		}
	}

}
