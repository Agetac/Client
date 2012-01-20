package org.agetac.controller;

import org.agetac.controller.sign.ISubController;
import org.agetac.tabs.sign.ITabActivity;

public class SITACController implements ISubController {
	
	private static final String TAG = "SITACController";
	
	private Controller parent;
	
	public SITACController(Controller controller) {
		parent = controller;
	}

	@Override
	public void processUpdate(ITabActivity act) {
		
	}

}
