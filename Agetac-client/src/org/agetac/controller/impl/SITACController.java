package org.agetac.controller.impl;

import org.agetac.command.*;
import org.agetac.command.impl.AddEntityCommand;
import org.agetac.command.impl.RemoveEntityCommand;
import org.agetac.controller.Controller;
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
		switch (act.getActionFlag()) {
		
		case ADD:
			parent.setLastEntity(act.getTouchedEntity());
			parent.getCommands().get(AddEntityCommand.NAME).execute(); 
			break;
		
		case REMOVE:
			parent.setLastEntity(act.getTouchedEntity());
			parent.getCommands().get(RemoveEntityCommand.NAME).execute();
			break;
		
		default : 
			android.util.Log.w(TAG, "FLAG inconnu!");
			break;
		}
	}

}
