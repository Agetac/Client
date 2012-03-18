package org.agetac.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.agetac.common.api.InterventionConnection;
import org.agetac.common.exception.BadResponseException;
import org.agetac.common.exception.InvalidJSONException;
import org.agetac.common.model.impl.Action;
import org.agetac.common.model.impl.Cible;
import org.agetac.common.model.impl.DemandeMoyen;
import org.agetac.common.model.impl.Implique;
import org.agetac.common.model.impl.Intervention;
import org.agetac.common.model.impl.Message;
import org.agetac.common.model.impl.Source;
import org.agetac.common.model.impl.Vehicule;
import org.agetac.entity.IEntity;
import org.agetac.network.ServerConnection;
import org.agetac.network.ServerThread;
import org.agetac.observer.MyObservable;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import com.google.gson.Gson;

public class InterventionEngine implements IInterventionEngine {

	private static final String TAG = "InterventionEngine";

	private Intervention intervention;
	private List<IEntity> entities;
	private MyObservable observable;
	private InterventionConnection iConn;

	public InterventionEngine(ServerConnection serv) {
		observable = new MyObservable();
		entities = new ArrayList<IEntity>();

		// TODO need auth ici pour récupérer l'intervention associée au COS
		// connecté
		Representation repr = serv.getResource("intervention", "1");

		try {
			JsonRepresentation jsonRepr = new JsonRepresentation(repr);
			intervention = new Gson().fromJson(jsonRepr.getReader(),
					Intervention.class);
			iConn = new InterventionConnection(intervention.getUniqueID(), serv);

		} catch (IOException e) {
			android.util.Log.d(TAG, e.getMessage());

		} catch (Exception e) {
			// XXX hack qui ne marche pas toujours... GREAT
			android.util.Log.d(TAG, "" + e.getMessage());
		}
	}

	@Override
	public void sendMessage(Message m) {
		try {
			iConn.putMessage(m);
		} catch (BadResponseException e) {
			android.util.Log.d(TAG, e.getMessage());

		}
	}

	public void demandeMoyen(DemandeMoyen dm) {
		// dm = iConn.putDemandeMoyen(dm);
		// ServerThread t = new ServerThread(iConn, dm);
		// t.start();
	}

	@Override
	public void addEntity(IEntity entity) {
		try {
			if (entity.getModel() instanceof Vehicule) {
				Vehicule v = (Vehicule) entity.getModel();
				// entity.setModel(iConn.putVehicule(v));
				iConn.putVehicule(v);
				entities.add(entity);

			} else if (entity.getModel() instanceof Action) {
				Action a = (Action) entity.getModel();
				// entity.setModel(iConn.putAction(a));
				iConn.putAction(a);
				entities.add(entity);

			} else if (entity.getModel() instanceof Source) {
				Source s = (Source) entity.getModel();
				// entity.setModel(iConn.putSource(s));
				iConn.putSource(s);
				entities.add(entity);

			} else if (entity.getModel() instanceof Cible) {
				Cible c = (Cible) entity.getModel();
				// entity.setModel(iConn.putCible(c));
				iConn.putCible(c);
				entities.add(entity);

			} else if (entity.getModel() instanceof Implique) {
				Implique i = (Implique) entity.getModel();
				// entity.setModel(iConn.putImplique(i));
				iConn.putImplique(i);
				entities.add(entity);
			}

		} catch (BadResponseException e) {
			android.util.Log.d(TAG, e.getMessage());

		} catch (Exception e) {
			android.util.Log.d(TAG, "" + e.getMessage());
		}

		notifyObservers();
	}

	@Override
	public void removeEntity(IEntity entity) {
		try {
			if (entity.getModel() instanceof Vehicule) {
				Vehicule v = (Vehicule) entity.getModel();
				iConn.deleteVehicule(v);
				entities.remove(entity);

			} else if (entity.getModel() instanceof Action) {
				Action a = (Action) entity.getModel();
				iConn.deleteAction(a);
				entities.remove(entity);

			} else if (entity.getModel() instanceof Source) {
				Source s = (Source) entity.getModel();
				iConn.deleteSource(s);
				entities.remove(entity);

			} else if (entity.getModel() instanceof Cible) {
				Cible c = (Cible) entity.getModel();
				iConn.deleteCible(c);
				entities.remove(entity);

			} else if (entity.getModel() instanceof Implique) {
				Implique i = (Implique) entity.getModel();
				iConn.deleteImplique(i);
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
