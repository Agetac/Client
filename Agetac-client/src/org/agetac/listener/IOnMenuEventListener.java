package org.agetac.listener;

import org.agetac.view.IPictogram;
import org.agetac.view.PictogramGroup;

public interface IOnMenuEventListener {

	public void onHideMenu();
	
	public void onShowMenu();
	
	public void onPictogramSelected(IPictogram p, PictogramGroup grp);
}
