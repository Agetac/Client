package org.agetac.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.agetac.entity.IEntity;
import org.agetac.exception.BadResponseException;
import org.agetac.model.exception.InvalidJSONException;
import org.agetac.model.impl.Action;
import org.agetac.model.impl.Cible;
import org.agetac.model.impl.Implique;
import org.agetac.model.impl.Intervention;
import org.agetac.model.impl.Message;
import org.agetac.model.impl.Source;
import org.agetac.model.impl.Vehicule;
import org.agetac.network.InterventionManager;
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
	private InterventionManager iManager;
	
	public InterventionEngine(ServerConnection serv) {
		observable = new MyObservable();
		entities = new ArrayList<IEntity>();
		
		// TODO need auth ici pour récupérer l'intervention associée au COS connecté
		Representation repr = serv.getResource("intervention", "1");
		
		try {
			JsonRepresentation jsonRepr = new JsonRepresentation(repr);
			intervention = new Intervention(jsonRepr.getJsonObject());
			iManager = new InterventionManager(intervention.getUniqueID(), serv);
			
		} catch (IOException e) {
			android.util.Log.d(TAG, e.getMessage());
			
		} catch (JSONException e) {
			android.util.Log.d(TAG, e.getMessage());
			
		} catch (InvalidJSONException e) {
			android.util.Log.d(TAG, e.getMessage());
		 
		} catch (Exception e) {
			// XXX hack : si on a pas ça plantage...
			android.util.Log.d(TAG, ""+e.getMessage());
		}
	}

	@Override
	public void addEntity(IEntity entity) {
		try {
			if (entity.getModel() instanceof Vehicule) {
				Vehicule v = (Vehicule) entity.getModel();
				android.util.Log.d(TAG, "iManager >>>> "+iManager);
				iManager.putVehicule(v);
				entities.add(entity);
			
			} else if (entity.getModel() instanceof Action) {
				Action a = (Action) entity.getModel();
				iManager.putAction(a);
				entities.add(entity);
			
			} else if (entity.getModel() instanceof Source) {
				Source s = (Source) entity.getModel();
				iManager.putSource(s);
				entities.add(entity);
			
			} else if (entity.getModel() instanceof Cible) {
				Cible c = (Cible) entity.getModel();
				iManager.putCible(c);
				entities.add(entity);
			
			} else if (entity.getModel() instanceof Message) {
				Message m = (Message) entity.getModel();
				iManager.putMessage(m);
				entities.add(entity);
			
			} else if (entity.getModel() instanceof Implique) {
				Implique i = (Implique) entity.getModel();
				iManager.putImplique(i);
				entities.add(entity);
			}
			
		} catch (BadResponseException e) {
			android.util.Log.d(TAG, e.getMessage());
			
		} catch (JSONException e) {
			android.util.Log.d(TAG, e.getMessage());
		}

		notifyObservers();
	}

	@Override
	public void removeEntity(IEntity entity) {
		try {
			if (entity.getModel() instanceof Vehicule) {
				Vehicule v = (Vehicule) entity.getModel();
				iManager.deleteVehicule(v);
				entities.remove(entity);
			
			} else if (entity.getModel() instanceof Action) {
				Action a = (Action) entity.getModel();
				iManager.deleteAction(a);
				entities.remove(entity);
			
			} else if (entity.getModel() instanceof Source) {
				Source s = (Source) entity.getModel();
				iManager.deleteSource(s);
				entities.remove(entity);
			
			} else if (entity.getModel() instanceof Cible) {
				Cible c = (Cible) entity.getModel();
				iManager.deleteCible(c);
				entities.remove(entity);
			
			} else if (entity.getModel() instanceof Message) {
				Message m = (Message) entity.getModel();
				iManager.deleteMessage(m);
				entities.remove(entity);
			
			} else if (entity.getModel() instanceof Implique) {
				Implique i = (Implique) entity.getModel();
				iManager.deleteImplique(i);
				entities.remove(entity);
			}
			
		} catch (BadResponseException e) {
			android.util.Log.d(TAG, e.getMessage());
		}
		
		notifyObservers();
	}

	@Override
	public void editEntity(IEntity entity) {
		android.util.Log.d(TAG, "[editEntity method] not implemented yet");
	}

	@Override
	public List<IEntity> getEntities() {
		return entities;
	}
	
	@Override
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
