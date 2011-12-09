package org.agetac.controller;

import java.util.Hashtable;
import java.util.Map;

import org.agetac.command.AddEntityCommand;
import org.agetac.command.ICommand;
import org.agetac.command.RemoveEntityCommand;
import org.agetac.model.Entity;
import org.agetac.model.Intervention;

public class Controller {

	private Entity lastEntity;
	private static Controller controller;
	private Map<String, ICommand> commands;
	private Intervention intervention;
	
	private Controller(Intervention intervention) {
		this.intervention = intervention;
		initCommands();
	}
	
	private void initCommands() {
		commands = new Hashtable<String, ICommand>();
		commands.put(AddEntityCommand.NAME, new AddEntityCommand(intervention, this));
		commands.put(RemoveEntityCommand.NAME, new RemoveEntityCommand(intervention, this));
	}
	
	public static Controller getInstance(Intervention intervention) {
		if (controller == null) controller = new Controller(intervention);
		return controller;
	}
	
	public Entity getLastEntity() {
		return lastEntity;
	}
	
	public void setLastEntity(Entity e) {
		lastEntity = e;
	}

	public Map<String, ICommand> getCommands() {
		return commands;
	}
}
