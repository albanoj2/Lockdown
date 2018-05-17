package com.lockdown.persist.store.util.helper;

import static org.junit.Assert.assertNotNull;

import com.lockdown.persist.store.util.data.cascade.domain.Child;
import com.lockdown.persist.store.util.data.cascade.domain.MockDomainObject;
import com.lockdown.persist.store.util.data.cascade.domain.Parent;

public class AssertChildUpdatedBeforeSavingParentListener implements OnSaveListener {

	@Override
	public void onSave(MockDomainObject object) {
		
		if (object instanceof Parent) {
			Parent savedParent = (Parent) object;
			
			for (Child child : savedParent.getFirstSubtree()) {
				assertNotNull(child.getId());
			}

			for (Child child : savedParent.getSecondSubtree()) {
				assertNotNull(child.getId());
			}

		}
	}

}
