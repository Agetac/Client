package org.agetac.command;

import java.util.Date;

import org.agetac.common.dto.MessageDTO;
import org.agetac.controller.Controller;
import org.agetac.memento.IMemento;

public class SendMessageCommand implements IRecordableCommand {
	public static final String NAME = "EnvoyerMessage";
	//private static int idMessage = 0;
	private static boolean messOk = false;



	private Controller controller;

	public SendMessageCommand(Controller controller) {
		this.controller = controller;

	}



	public static boolean getMessOk () {

		return messOk;
	}

	

	public void sendMessage(String message)  {

		
		
		Date date = new Date();
		MessageDTO mess = new MessageDTO (message, date);
		System.out.println("Message : " + message + "," + date);
		
		
		messOk = controller.getInterventionEngine().sendMessage(mess);
		
		
	}


	public void execute() {

		sendMessage(controller.getMessage());
		//idMessage++;
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