package org.agetac.controller;

import org.agetac.tabs.ITabActivity;

public class MessagesController implements ISubController {
	
	private Controller parent;
	
	public MessagesController(Controller controller) {
		parent = controller;
	}

	@Override
	public void processUpdate(ITabActivity act) {
		// TODO Auto-generated method stub

	}

}
