package org.agetac.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Agent implements IEntity {

	public static final String RESOURCE = "agent";
	
	private String nom;
	private String aptitude;
	private Agent superieur = null;
	private List<Agent> subordonnes = null;
	private String uniqueID;
	
	public Agent() {
		super();
		this.nom = "";
		this.aptitude = null;
		this.superieur = null;
		this.subordonnes = null;
	}
	
	public Agent(String uniqueID, String nom, String aptitude, Agent superieur,
			List<Agent> subordonnes) {
		super();
		this.nom = nom;
		this.aptitude = aptitude;
		this.superieur = superieur;
		this.subordonnes = subordonnes;
		this.uniqueID = uniqueID;
	}
	public Agent(JSONObject json) {
		try {
			this.uniqueID = json.getString("uniqueID");
			this.nom = json.getString("nom");
			this.aptitude = json.getString("aptitude");
			//this.superieur = (Agent) json.get("superieur");
			//this.subordonnes = (List<Agent>) json.get("subordonnes");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUniqueId() {
		return this.uniqueID;
	}

	public void setId(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAptitude() {
		return aptitude;
	}

	public void setAptitude(String aptitude) {
		this.aptitude = aptitude;
	}

	public Agent getSuperieur() {
		return superieur;
	}

	public void setSuperieur(Agent superieur) {
		this.superieur = superieur;
	}

	public List<Agent> getSubordonnes() {
		return subordonnes;
	}

	public void setSubordonnes(List<Agent> subordonnes) {
		this.subordonnes = subordonnes;
	}

	/**
	 * Convert this object to a string for representation
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[Agent ");
		sb.append("uniqueID:");
		sb.append(this.uniqueID);
		sb.append(",nom:");
		sb.append(this.nom);
		sb.append(",aptitude:");
		sb.append(this.aptitude);
		sb.append(",superieur:");
		sb.append(this.superieur);
		sb.append(",subordonnes:");
		sb.append(this.subordonnes);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Convert this object to a JSON object for representation
	 */
	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("uniqueID", this.uniqueID);
			json.put("nom", this.nom);
			json.put("aptitude", this.aptitude);
			//json.append("superieur", this.superieur);
			//json.append("subordonnes", this.subordonnes);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("test "+json.toString());
		return json.toString();
	}

	public String getUniqueID() {
		return this.uniqueID;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDeBase() {
		// TODO Auto-generated method stub
		return false;
	}

}