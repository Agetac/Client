package org.agetac.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.agetac.entity.sign.IEntity;
import org.agetac.model.sign.AbstractModel;
import org.agetac.model.sign.IJsonable;
import org.agetac.observer.MyObservable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Intervention extends AbstractModel {
	
	private static final String TAG = "Intervention";

	private List<Vehicule> vehicules;
	private List<Cible> cibles;
	private List<Source> sources;
	private List<Action> actions;
	private List<Message> messages;
	private List<Implique> impliques;
	
	private List<IEntity> entities;
	private MyObservable observable;

	public Intervention(String uid) {
		super(uid, null, new Position(0, 0));
		
		entities = new ArrayList<IEntity>();
		observable = new MyObservable();

		this.vehicules = new ArrayList<Vehicule>();
		this.cibles = new ArrayList<Cible>();
		this.sources = new ArrayList<Source>();
		this.actions = new ArrayList<Action>();
		this.messages = new ArrayList<Message>();
		this.impliques = new ArrayList<Implique>();
	}

	public Intervention(JSONObject json) {
		super(json);
		
		entities = new ArrayList<IEntity>();
		observable = new MyObservable();
		
		this.vehicules = new ArrayList<Vehicule>();
		this.cibles = new ArrayList<Cible>();
		this.sources = new ArrayList<Source>();
		this.actions = new ArrayList<Action>();
		this.messages = new ArrayList<Message>();
		this.impliques = new ArrayList<Implique>();

		try {
			JSONArray jsar = json.getJSONArray("moyens");
			for (int i = 0; i < jsar.length(); i++) {
				vehicules.add(new Vehicule(jsar.getJSONObject(i)));
			}

			jsar = json.getJSONArray("cibles");
			for (int i = 0; i < jsar.length(); i++) {
				cibles.add(new Cible(jsar.getJSONObject(i)));
			}

			jsar = json.getJSONArray("sources");
			for (int i = 0; i < jsar.length(); i++) {
				sources.add(new Source(jsar.getJSONObject(i)));
			}

			jsar = json.getJSONArray("actions");
			for (int i = 0; i < jsar.length(); i++) {
				actions.add(new Action(jsar.getJSONObject(i)));
			}

			jsar = json.getJSONArray("messages");
			for (int i = 0; i < jsar.length(); i++) {
				messages.add(new Message(jsar.getJSONObject(i)));
			}

			jsar = json.getJSONArray("impliques");
			for (int i = 0; i < jsar.length(); i++) {
				impliques.add(new Implique(jsar.getJSONObject(i)));
			}
			notifyObservers();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return "Intervention [moyens=" + vehicules + ", position=" + position + "]";
	}

	public JSONObject toJson() {
		JSONObject json = super.toJson();
		try {

			json.put("moyens", this.vehicules);
			json.put("cibles", this.cibles);
			json.put("sources", this.sources);
			json.put("actions", this.actions);
			json.put("messages", this.messages);
			json.put("impliques", this.impliques);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}

	@Override
	public IJsonable fromJson(JSONObject json) {
		return new Intervention(json);
	}

	/*
	 * GETTER & SETTER
	 */
	public List<Vehicule> getVehicules() {
		return vehicules;
	}

	public void setVehicules(List<Vehicule> vehicules) {
		this.vehicules = vehicules;
		notifyObservers();
	}

	public List<Cible> getCibles() {
		return cibles;
	}

	public void setCibles(List<Cible> cibles) {
		this.cibles = cibles;
		notifyObservers();
	}

	public List<Source> getSources() {
		return sources;
	}

	public void setSources(List<Source> sources) {
		this.sources = sources;
		notifyObservers();
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
		notifyObservers();
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
		notifyObservers();
	}

	public List<Implique> getImpliques() {
		return impliques;
	}

	public void setImpliques(List<Implique> impliques) {
		this.impliques = impliques;
		notifyObservers();
	}
	
	private void notifyObservers() {
		observable.setChanged();
		observable.notifyObservers(this);
	}
	
	public void addObserver(Observer obs) {
		observable.addObserver(obs);
	}

	public List<IEntity> getEntities() {
		return entities;
	}

	public void removeEntity(IEntity entity) {
		entities.remove(entity);
		notifyObservers();
	}
	
	public void addEntity(IEntity entity) {
		entities.add(entity);
		notifyObservers();
	}
}
