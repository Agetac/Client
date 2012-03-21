package org.agetac.network;

import org.agetac.R;
import org.agetac.common.api.ServerApi;
import org.agetac.common.exception.BadResponseException;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import android.content.Context;

public class ServerConnection implements ServerApi {
	
	private static final String TAG = "ServerConnection";

	private String host;
	private String contextRoot;
	private String port;

	public ServerConnection(Context c) {
		this.host = c.getString(R.string.host);
		this.port = c.getString(R.string.port);
		this.contextRoot = c.getString(R.string.contextRoot);
	}

	@Override
	public Representation getResource(String resType, String resUniqueID) {
		
		String url = baseUrl() + resType;
		
		if(resUniqueID != null){
			url +=  "/" + resUniqueID;
		}
		
		System.out.println("GET : " + url);
		
		ClientResource client = new ClientResource(url);
		Representation repr = null;
		
		repr = client.get();
		
		return repr;
	}

	@Override
	public Representation putResource(String resType, String resUniqueID, Representation resRepresentation) throws BadResponseException{

		String url = baseUrl() + resType;
		
		if(resUniqueID != null){
			url += "/" + resUniqueID;
		}
		System.out.println("PUT : " + url);
		ClientResource client = new ClientResource(url);
		
		try {
			return client.put(resRepresentation);
		} catch (ResourceException e) {
			throw (new BadResponseException(client.getResponse()));
		}
		
	}

	@Override
	public Representation postResource(String resType, String resUniqueID, Representation resRepresentation) throws BadResponseException {

		String url = baseUrl() + resType;
		
		if(resUniqueID != null){
			url += "/" + resUniqueID;
		}
		System.out.println("POST : " + url);
		ClientResource client = new ClientResource(url);
		
		try {
			return client.post(resRepresentation);
		} catch (ResourceException e) {
			throw (new BadResponseException(client.getResponse()));
		}
		
	}

	@Override
	public void deleteResource(String resType, String resUniqueID) {

		String url = baseUrl() + resType;
		
		if(resUniqueID != null){
			url += "/" + resUniqueID;
		}
		System.out.println("DEL : " + url);
		ClientResource client = new ClientResource(url);
		
		try {
			client.delete();
		} catch (ResourceException e) {
			System.out.println("Error: " + e.getStatus());
		}
		
	}

	private String baseUrl() {
		return "http://" + host + ":" + port + "/" + contextRoot + "/";
	}
}
