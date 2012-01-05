package org.agetac.tabs;

import org.agetac.model.IEntity;
import org.agetac.model.ActionFlag;

public interface ITabActivity {

	public ActionFlag getActionFlag();
	
	public IEntity getTouchedEntity();
	
	public void update();
}
