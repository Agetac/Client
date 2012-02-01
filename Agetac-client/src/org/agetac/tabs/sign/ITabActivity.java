package org.agetac.tabs.sign;

import org.agetac.model.ActionFlag;
import org.agetac.model.sign.IEntity;

public interface ITabActivity {

	public ActionFlag getActionFlag();
	
	public IEntity getTouchedEntity();
	
	public String getMessage();
	
	public void update();
}
