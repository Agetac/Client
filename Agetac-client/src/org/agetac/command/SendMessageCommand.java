package org.agetac.command;

import org.agetac.command.sign.IRecordableCommand;
import org.agetac.controller.Controller;
import org.agetac.memento.sign.IMemento;
import org.agetac.model.impl.Intervention;

public class SendMessageCommand implements IRecordableCommand {
	public static final String NAME = "EnvoyerMessage";
	public static int idMessage = 0;


	private Intervention intervention;
	private Controller controller;

	public SendMessageCommand(Controller controller) {
		this.controller = controller;
		this.intervention = controller.getIntervention();

	}
	
	public void execute() {
		
//		intervention.erMessage(controller.getMessage());
		idMessage++;
	}

	@Override
	public IMemento saveMemento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restoreMemento(IMemento mem) {
		// TODO Auto-generated method stub
		
	}
}