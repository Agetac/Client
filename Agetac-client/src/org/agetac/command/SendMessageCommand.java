package org.agetac.command;

import java.io.IOException;
import java.util.Date;

import org.agetac.common.model.impl.Message;
import org.agetac.controller.Controller;
import org.agetac.entity.Entity;
import org.agetac.memento.IMemento;

public class SendMessageCommand implements IRecordableCommand {
	public static final String NAME = "EnvoyerMessage";
	private static int idMessage = 0;
	private static boolean messOk = false;

	private Controller controller;

	public SendMessageCommand(Controller controller) {
		this.controller = controller;

	}

	public static void setMessOk(boolean b) {

		messOk = b;
	}

	public static boolean getMessOk() {

		return messOk;
	}

	public String getGroupeHoraire() {

		Date d = new Date();
		String res = "";

		int minutes = d.getMinutes();
		int heures = d.getHours();

		if (heures < 10) {
			res = "0" + heures;
		} else {
			res = "" + heures;
		}

		if (minutes < 10) {
			res = res + "0" + minutes;
		} else {
			res = res + minutes;
		}

		return res;
	}

	public void sendMessage(String message) throws IOException {

		// TODO c'pas possible ça ...
		// la connexion au serveur est déjà faite dans
		// org.agetac.network.ServerConnection

		// String date = getGroupeHoraire();
		// String id = "" + SendMessageCommand.idMessage;
		// Message mess = new Message ( id, message, date);
		// System.out.println("Message : "+id + "," + message + "," + date);
		//
		// String interId = "0";
		// ServerConnection serv = new ServerConnection("148.60.13.116", "8112",
		// "agetacserver");
		//
		//
		// Representation r = null;
		// try {
		// r = new JsonRepresentation(mess.toJSON());
		// serv.putResource("intervention/" + interId + "/message",
		// mess.getUniqueID(), r);
		// messOk = true;
		//
		// } catch (JSONException e) {
		//
		// System.out.println("Le message n'a pas pu �tre envoy� sur le serveur");
		// e.printStackTrace();
		// }
		//
		//
		//
		//
		// Representation rep = serv.getResource("intervention/" + interId +
		// "/message",
		// mess.getUniqueID());
		// JsonRepresentation jrepr = new JsonRepresentation(rep);
		//
		//
		// System.out.println(new Message(jrepr.getJsonObject()).getMessage());

	}

	public void execute() {

		try {
			sendMessage(controller.getMessage());
		} catch (IOException e) {
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