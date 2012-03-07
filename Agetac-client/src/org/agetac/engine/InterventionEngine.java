package org.agetac.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.agetac.engine.sign.IInterventionEngine;
import org.agetac.entity.sign.IEntity;
import org.agetac.model.exception.InvalidJSONException;
import org.agetac.model.impl.Action;
import org.agetac.model.impl.Intervention;
import org.agetac.model.impl.Vehicule;
import org.agetac.network.ServerConnection;
import org.agetac.observer.MyObservable;
import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

public class InterventionEngine implements IInterventionEngine {

	private static final String TAG = "InterventionEngine";
	
	private Intervention intervention;
	private List<IEntity> entities;
	private MyObservable observable;
	private ServerConnection servConn;
	
	public InterventionEngine(ServerConnection conn) {
		servConn = conn;
		Representation repr = servConn.getResource("intervention", "1");
		
		try {
			JsonRepresentation jsonRepr = new JsonRepresentation(repr);
			intervention = new Intervention(jsonRepr.getJsonObject());
			
		} catch (IOException e) {
			android.util.Log.d(TAG, e.getMessage());
			
		} catch (InvalidJSONException e) {
			android.util.Log.d(TAG, e.getMessage());
			
		} catch (JSONException e) {
			android.util.Log.d(TAG, e.getMessage());
		}		
		
		observable = new MyObservable();
		entities = new ArrayList<IEntity>();
	}
	
	@Override
	public void addEntity(IEntity entity) {
		if (entity.getModel() instanceof Vehicule) {
			intervention.getVehicules().add((Vehicule) entity.getModel());
			entities.add(entity);
			
		}
		if (entity.getModel() instanceof Action) {
			intervention.getActions().add((Action) entity.getModel());
			entities.add(entity);
		}
		notifyObservers();
	}

	@Override
	public void removeEntity(IEntity entity) {
		if (entity.getModel() instanceof Vehicule) {
			intervention.getVehicules().remove((Vehicule) entity.getModel());
			entities.remove(entity);
		}
		if (entity.getModel() instanceof Action) {
			intervention.getActions().remove((Action) entity.getModel());
			entities.remove(entity);
		}
		notifyObservers();
	}

	@Override
	public void editEntity(String uid, IEntity entity) {
		android.util.Log.d(TAG, "not implemented yet");
	}

	@Override
	public List<IEntity> getEntities() {
		return entities;
	}
	
	public Intervention getIntervention() {
		return intervention;
	}
	
	private void notifyObservers() {
		observable.setChanged();
		observable.notifyObservers(intervention);
	}

	public void addObserver(Observer observer) {
		observable.addObserver(observer);
	}
}
