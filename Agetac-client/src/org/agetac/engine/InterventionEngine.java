package org.agetac.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.agetac.common.api.InterventionApi;
import org.agetac.common.api.InterventionConnection;
import org.agetac.common.exception.BadResponseException;
import org.agetac.common.model.impl.Action;
import org.agetac.common.model.impl.Cible;
import org.agetac.common.model.impl.DemandeMoyen;
import org.agetac.common.model.impl.DemandeMoyen.EtatDemande;
import org.agetac.common.model.impl.Implique;
import org.agetac.common.model.impl.Intervention;
import org.agetac.common.model.impl.Message;
import org.agetac.common.model.impl.Source;
import org.agetac.common.model.impl.Vehicule;
import org.agetac.common.model.sign.AbstractModel;
import org.agetac.common.model.sign.IModel;
import org.agetac.entity.EntityList;
import org.agetac.entity.IEntity;
import org.agetac.network.ServerConnection;
import org.agetac.observer.MyObservable;
import org.agetac.view.EntityHolder;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import android.content.Context;

public class InterventionEngine implements IInterventionEngine {

	private static final String TAG = "InterventionEngine";
	
	private Intervention intervention;
	private EntityList entities;
	private MyObservable observable;
	private InterventionApi iConn;
	private UpdateInterventionThread updateThread;
	private Context context;
	
	public InterventionEngine(final ServerConnection serv, final Context c) {
		observable = new MyObservable();
		entities = new EntityList();
		this.context = c;
		
		// TODO need auth ici pour récupérer l'intervention associée au COS connecté
		final Representation repr = serv.getResource("intervention", "1");
				
		try {
			JsonRepresentation jRepr = new JsonRepresentation(repr);
			ObjectMapper mapper = new ObjectMapper();
			
			intervention = mapper.readValue(jRepr.getStream(), Intervention.class);
			iConn = new InterventionConnection(intervention.getUniqueID(), serv);
			
			// thread de MAJ de l'intervention via le serveur en "temps-reel"
			// TODO changer ça pour utiliser un système PUSH afin
			// que ce soit le serveur qui demande au client de se mettre à jour
			// et pas le client qui flood le serveur
			updateThread = new UpdateInterventionThread(this, iConn);
			updateThread.start();
			
		} catch (IOException e) {
			android.util.Log.e(TAG, "IOException: "+e.getMessage());
		}
	}
	
	@Override
	public boolean sendMessage(Message m) {
		
		try {
			iConn.putMessage(m);
			return true;
		} catch (BadResponseException e) {
			android.util.Log.d(TAG, e.getMessage());
			
		} catch (JSONException e) {
			android.util.Log.d(TAG, e.getMessage());
		}
		return false;
	}
	
	@Override
	public void addEntity(IEntity entity) {		
		try {
			if (entity.getModel() instanceof Action) {
				Action a = (Action) entity.getModel();
				entity.setModel(iConn.putAction(a));
				entities.add(entity);
			
			} else if (entity.getModel() instanceof Source) {
				Source s = (Source) entity.getModel();
				entity.setModel(iConn.putSource(s));
				entities.add(entity);
			
			} else if (entity.getModel() instanceof Cible) {
				Cible c = (Cible) entity.getModel();
				entity.setModel(iConn.putCible(c));
				entities.add(entity);
			
			} else if (entity.getModel() instanceof Implique) {
				Implique i = (Implique) entity.getModel();
				entity.setModel(iConn.putImplique(i));
				entities.add(entity);
				
			} else if (entity.getModel() instanceof DemandeMoyen) {
				DemandeMoyen dm = (DemandeMoyen) entity.getModel();
				DemandeMoyen dmWithID = iConn.putDemandeMoyen(dm);
				entity.setModel(dmWithID);
				entities.add(entity);
			}
			
		} catch (BadResponseException e) {
			android.util.Log.e(TAG, "addEntity BadResponse: "+e.getMessage());
			
		} catch (JSONException e) {
			android.util.Log.e(TAG, "addEntity JSONException: "+e.getMessage());
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
			
			} else if (entity.getModel() instanceof DemandeMoyen) {
				DemandeMoyen dm = (DemandeMoyen) entity.getModel();
				iConn.deleteDemandeMoyen(dm);
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
	public void updateIntervention(Intervention inter) {
		EntityHolder holder = EntityHolder.getInstance(context);
		
		List<Vehicule> vehList = inter.getVehicules();
		processUpdate(vehList, Vehicule.class);
		
		List<Action> actList = inter.getActions();
		processUpdate(actList, Action.class);
		
		List<Cible> cibList = inter.getCibles();
		processUpdate(cibList, Cible.class);
		
		List<DemandeMoyen> dMoyList = inter.getDemandesMoyen();
		for (int i=0; i<dMoyList.size(); i++) {
			// traiter les demandes acceptées et les supprimers de la sitac
			// pour les remplacers par des vehicules
			IEntity e = entities.find(dMoyList.get(i).getUniqueID(), DemandeMoyen.class);
			// si la demande existe deja cote client
			if (e != null) {
				// on met à jour le model de son entitee
				e.setModel(dMoyList.get(i));
				// on cherche à savoir si son état est "ACCEPTE"
				if (dMoyList.get(i).getEtat() == EtatDemande.ACCEPTEE) {
					// la demande a ete acceptee, il faut donc supprimer
					// la demande de la SITAC pour la remplacer par un vehicule
					for (int k=0; k<intervention.getVehicules().size(); k++) {
						if (intervention.getVehicules().get(k).getUniqueID() == dMoyList.get(i).getVehId()) {
							// on cree la future entitee du vehicule
							Vehicule v = intervention.getVehicules().get(k);
							entities.add(holder.generateEntity(v));
							// on supprime la demande de la SITAC
							entities.remove(dMoyList.get(i));
						}
					}
				}
			}
		}
		
		List<Implique> impList = inter.getImpliques();
		processUpdate(impList, Implique.class);
		
		List<Message> messList = inter.getMessages();
		// TODO process messages differently
		
		List<Source> srcList = inter.getSources();
		processUpdate(srcList, Source.class);
		
		notifyObservers();
	}
	
	/**
	 * Parcours la liste passee en parametre afin de mettre a jour les entitees
	 * deja connues ou bien de creer des entitees pour les objets qu'elle ne
	 * connait pas.
	 * @param list une liste qui etend IModel
	 * @param aClass la classe des objets de la liste
	 */
	private void processUpdate(List<? extends IModel> list, Class<? extends IModel> aClass) {
		EntityHolder holder = EntityHolder.getInstance(context);
		
		for (int i=0; i<list.size(); i++) {
			IEntity e = entities.find(list.get(i).getUniqueID(), aClass);
			// si l'entitee existe deja cote client
			if (e != null) {
				// on met à jour son model
				e.setModel(list.get(i));
				
			} else {
				// sinon on lui cree une entitee
				IModel model = list.get(i);
				entities.add(holder.generateEntity(model));
			}
		}
	}

	@Override
	public ArrayList<IEntity> getEntities() {
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

	@Override
	public void stopUpdates() {
		if (updateThread != null) updateThread.doStop();
	}
}
