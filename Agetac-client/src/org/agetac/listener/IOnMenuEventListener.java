package org.agetac.listener;

import org.agetac.view.IPictogram;
import org.agetac.view.MenuGroup;

public interface IOnMenuEventListener {

	public void onHideMenu();
	
	public void onShowMenu();
	
	public void onPictogramSelected(IPictogram p, MenuGroup grp);
}
