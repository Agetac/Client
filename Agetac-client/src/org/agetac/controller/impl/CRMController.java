package org.agetac.controller.impl;

import org.agetac.controller.Controller;
import org.agetac.controller.sign.ISubController;
import org.agetac.tabs.sign.ITabActivity;

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