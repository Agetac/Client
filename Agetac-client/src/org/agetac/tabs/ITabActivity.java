package org.agetac.tabs;

import org.agetac.model.Entity;
import org.agetac.model.ActionFlag;

public interface ITabActivity {

	public ActionFlag getActionFlag();
	
	public Entity getTouchedEntity();
	
	public void update();
}
