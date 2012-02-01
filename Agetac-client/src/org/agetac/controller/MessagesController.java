package org.agetac.controller;

import org.agetac.command.EnvoyerMessageCommand;
import org.agetac.controller.sign.ISubController;
import org.agetac.tabs.sign.ITabActivity;

public class MessagesController implements ISubController {
	
	private Controller parent;
	private static final String TAG = "MessagesController";
	
	public MessagesController(Controller controller) {
		parent = controller;
	}

	@Override
	public void processUpdate(ITabActivity act) {
		switch(act.getActionFlag()){
		
			case SEND_MESSAGE:
				parent.setMessage(act.getMessage());
				parent.getCommands().get(EnvoyerMessageCommand.NAME).execute();
				break;
					
			default:
				android.util.Log.w(TAG, "FLAG inconnu!");
				break;
		}

	}

}
