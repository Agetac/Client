package org.agetac.command;

import java.io.IOException;
import java.util.Date;

import org.agetac.client.ServerConnection;
import org.agetac.client.exception.BadResponseException;
import org.agetac.controller.Controller;
import org.agetac.memento.IMemento;
import org.agetac.model.impl.Message;
import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

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

	
	public boolean sendMessage(String message) throws BadResponseException, IOException, JSONException {
		
		String date = getGroupeHoraire();
		String id = "" + SendMessageCommand.idMessage;
		Message mess = new Message ( id, message, date);
		System.out.println("Message : "+id + "," + message + "," + date);
		
		String interId = "0";
		ServerConnection serv = new ServerConnection("148.60.13.116", "8112", "agetacserver");
		
		Representation r = null;
		try {
			r = new JsonRepresentation(mess.toJSON());
		} catch (JSONException e) {
			
			e.printStackTrace();
		}

		serv.putResource("intervention/" + interId + "/message",
				mess.getUniqueID(), r);
		
		
		Representation rep = serv.getResource("intervention/" + interId + "/message",
				mess.getUniqueID());
		JsonRepresentation jrepr = new JsonRepresentation(rep);
		
		
		System.out.println(new Message(jrepr.getJsonObject()).getMessage());

		return true;
	}
		
		
	public void execute() {
		
		try {
			try {
				sendMessage(controller.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (BadResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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