package org.agetac.controller;

import org.agetac.tabs.ITabActivity;

import android.app.Activity;

public class CRMController implements ISubController {

	private Controller parent;
	
	public CRMController(Controller controller) {
		parent = controller;
	}

	@Override
	public void processUpdate(ITabActivity act) {
		// TODO Auto-generated method stub
		
	}

}
