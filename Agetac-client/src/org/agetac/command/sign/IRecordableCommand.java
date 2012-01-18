package org.agetac.command.sign;

import org.agetac.memento.sign.IMemento;

public interface IRecordableCommand extends ICommand {

	public IMemento saveMemento();
	
	public void restoreMemento(IMemento mem);
}
