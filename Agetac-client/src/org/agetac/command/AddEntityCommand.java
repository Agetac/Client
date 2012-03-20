package org.agetac.command;

import org.agetac.controller.Controller;
import org.agetac.engine.IInterventionEngine;
import org.agetac.memento.IMemento;

public class AddEntityCommand implements IRecordableCommand {
	
	public static final String NAME = "AddEntity";
	
	private IInterventionEngine engine;
	private Controller controller;

	public AddEntityCommand(Controller controller) {
		this.controller = controller;
		this.engine = controller.getInterventionEngine();
	}

	@Override
	public void execute() {
		System.out.println("before entity>>> "+controller.getLastEntity().toString());
		engine.addEntity(controller.getLastEntity());
		System.out.println("after entity>>> "+controller.getLastEntity().toString());
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
