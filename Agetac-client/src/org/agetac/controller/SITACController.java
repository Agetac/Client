package org.agetac.controller;

import org.agetac.command.AddEntityCommand;
import org.agetac.command.RemoveEntityCommand;
import org.agetac.tabs.ITabActivity;

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
