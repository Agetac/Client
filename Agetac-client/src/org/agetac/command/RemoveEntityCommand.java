package org.agetac.command;

import org.agetac.controller.Controller;
import org.agetac.memento.IMemento;
import org.agetac.model.Intervention;

public class RemoveEntityCommand implements IRecordableCommand {

	public static final String NAME = "RemoveEntity";
	
	private Intervention intervention;
	private Controller controller;
	
	public RemoveEntityCommand(Intervention intervention, Controller controller) {
		this.intervention = intervention;
		this.controller = controller;
	}
	
	@Override
	public void execute() {
		intervention.removeEntity(controller.getLastEntity());
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
