package org.agetac.listener;

import org.agetac.pictogram.PictogramGroup;
import org.agetac.pictogram.sign.IPictogram;

public interface IOnMenuEventListener {

	public void onHideMenu();
	
	public void onShowMenu();
	
	public void onPictogramSelected(IPictogram p, PictogramGroup grp);
}
