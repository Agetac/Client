package org.agetac.command;

import org.agetac.controller.Controller;
import org.agetac.memento.AddEntityMemento;
import org.agetac.memento.IMemento;
import org.agetac.model.Intervention;

public class AddEntityCommand implements IRecordableCommand {
	
	public static final String NAME = "AddEntity";
	
	private Intervention intervention;
	private Controller controller;

	public AddEntityCommand(Intervention intervention, Controller controller) {
		this.intervention = intervention;
		this.controller = controller;
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
