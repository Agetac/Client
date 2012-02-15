package org.agetac.tabs.sign;

import org.agetac.common.ActionFlag;
import org.agetac.entity.sign.IEntity;

public interface ITabActivity {

	public ActionFlag getActionFlag();
	
	public IEntity getTouchedEntity();
	
	public String getMessage();
	
	public void update();
}
