package org.agetac.controller;

import org.agetac.command.AddEntityCommand;
import org.agetac.command.RemoveEntityCommand;
import org.agetac.tabs.ITabActivity;

public class SOEICController implements ISubController {
	
	private static final String TAG = "SOEICController";
	
	private Controller parent;
	
	public SOEICController(Controller controller) {
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
				
			default:
				android.util.Log.w(TAG, "FLAG inconnu!");
				break;
	}
	}
}
