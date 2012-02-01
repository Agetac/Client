package org.agetac.command;

import org.agetac.controller.Controller;
import org.agetac.memento.IMemento;
import org.agetac.model.Intervention;

public class EnvoyerMessageCommand implements IRecordableCommand {
	public static final String NAME = "EnvoyerMessage";
	public static int idMessage = 0;


	private Intervention intervention;
	private Controller controller;

	public EnvoyerMessageCommand(Controller controller) {
		this.controller = controller;
		this.intervention = controller.getIntervention();

	}
	
	public void execute() {
		
		intervention.EnvoyerMessage(controller.getMessage());
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