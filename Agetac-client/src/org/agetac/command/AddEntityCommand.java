package org.agetac.command;

import org.agetac.command.sign.IRecordableCommand;
import org.agetac.controller.Controller;
import org.agetac.memento.AddEntityMemento;
import org.agetac.memento.sign.IMemento;
import org.agetac.model.Intervention;

public class AddEntityCommand implements IRecordableCommand {
	
	public static final String NAME = "AddEntity";
	
	private Intervention intervention;
	private Controller controller;

	public AddEntityCommand(Controller controller) {
		this.controller = controller;
		this.intervention = controller.getIntervention();
	}

	@Override
	public void execute() {
		intervention.addEntity(controller.getLastEntity());
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
