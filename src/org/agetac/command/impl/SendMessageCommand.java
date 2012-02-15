package org.agetac.command.impl;

import java.util.Date;

import org.agetac.command.sign.IRecordableCommand;
import org.agetac.controller.Controller;
import org.agetac.memento.sign.IMemento;
import org.agetac.model.impl.Message;
import org.restlet.engine.http.connector.ServerConnection;

public class SendMessageCommand implements IRecordableCommand {
	public static final String NAME = "EnvoyerMessage";
	public static int idMessage = 0;


	private Controller controller;

	public SendMessageCommand(Controller controller) {
		this.controller = controller;

	}
	
	
	public String getGroupeHoraire(){
		
		Date d = new Date();
		String res="";
		
		int minutes = d.getMinutes();
		int heures = d.getHours();
		
		if(heures < 10 ) {res = "0" + heures;}
		else {res = "" + heures;}
		
		if (minutes < 10) {res = res +"0" + minutes;}
		else { res = res + minutes;}
		
		return res;
	}

	
	public Message sendMessage(String message) {
		
		String date = getGroupeHoraire();
		String id = "" + SendMessageCommand.idMessage;
		Message mess = new Message ( id, message, date);
		System.out.println("Message : "+id + "," + message + "," + date);
		

		
		return mess;
	}
	
	
	public void execute() {
		
		sendMessage(controller.getMessage());
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