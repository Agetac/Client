package org.agetac.network;

import java.text.MessageFormat;

import org.agetac.common.Configuration;
import org.agetac.model.Agent;
import org.agetac.model.sign.IEntity;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ClientResource;

public class ServerManager {

	private static ServerManager instance = new ServerManager();
	
	private ServerManager() {}
	
	public static ServerManager getInstance() {
		return instance;
	}
	
	public Agent getAgent(String id) {
		ClientResource client = new ClientResource(MessageFormat.format(
				"{0}/{1}/{2}", Configuration.BASE_URL, Agent.RESOURCE, id));
		Agent agent = (Agent) getEntity(client);
		return agent;
	}
	
	private IEntity getEntity(ClientResource client) {
		IEntity entity = null;
		try {
			System.out.println("avant client.get()");
			JsonRepresentation representation = new JsonRepresentation(client.get());
			System.out.println("après client.get()");
			entity = new Agent(representation.getJsonObject());
			System.out.println("tout passé");
			
		} catch (Exception e) {
			// If the operation didn't succeed, indicate why here.
			System.out.println("Error: " + e.getMessage());
		}
		return entity;
	}
}
