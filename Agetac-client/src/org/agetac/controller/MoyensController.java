package org.agetac.controller;

import org.agetac.command.RemoveEntityCommand;
import org.agetac.tabs.ITabActivity;

public class MoyensController implements ISubController {
	
	private static final String TAG = "MoyensController";
	
	private Controller parent;
	
	public MoyensController(Controller controller) {
		parent = controller;
	}

	@Override
	public void processUpdate(ITabActivity act) {
		switch (act.getActionFlag()) {
			case REMOVE:
				parent.setLastEntity(act.getTouchedEntity());
				parent.getCommands().get(RemoveEntityCommand.NAME).execute();
				break;
				
			default:
				android.util.Log.w(TAG, "FLAG inconnu!");
				break;
		}
	}
}
