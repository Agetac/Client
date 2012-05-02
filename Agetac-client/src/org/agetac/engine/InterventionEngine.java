package org.agetac.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.agetac.R;
import org.agetac.common.client.AgetacClient;
import org.agetac.common.dto.ActionDTO;
import org.agetac.common.dto.IModel;
import org.agetac.common.dto.InterventionDTO;
import org.agetac.common.dto.MessageDTO;
import org.agetac.common.dto.SourceDTO;
import org.agetac.common.dto.TargetDTO;
import org.agetac.common.dto.VehicleDTO;
import org.agetac.common.dto.VehicleDemandDTO;
import org.agetac.common.dto.VehicleDemandDTO.DemandState;
import org.agetac.common.dto.VictimDTO;
import org.agetac.common.observer.MyObservable;
import org.agetac.entity.EntityFactory;
import org.agetac.entity.EntityList;
import org.agetac.entity.IEntity;
import org.agetac.handler.AddHandler;
import org.agetac.handler.DeleteHandler;
import org.agetac.handler.UpdateHandler;
import org.restlet.engine.Engine;
import org.restlet.ext.jackson.JacksonConverter;

import android.content.Context;

public class InterventionEngine implements IInterventionEngine {

	private static final String TAG = "InterventionEngine";
	
	private InterventionDTO intervention;
	private EntityList entities;
	private MyObservable observable;
	private AgetacClient client;
	private UpdateInterventionThread updateThread;
	private AddHandler addHandler;
	private DeleteHandler delHandler;
	private UpdateHandler updateHandler;
	private List<MessageDTO> listMessages;
	private int interId = -1;
	
	public InterventionEngine(final Context c) {
		observable = new MyObservable();
		entities = new EntityList();
		
		String host = c.getString(R.string.host);
		int port = Integer.valueOf(c.getString(R.string.port));
		
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		client = new AgetacClient(host, port);
		
		// FIXME recuperer l'id de l'intervention via login/mdp avec
		// intervention liee a un compte
		interId = 0;
		intervention = client.getIntervention(interId);
			
		// cree les handlers pour les operations REST
		addHandler = new AddHandler(entities, client, interId);
		delHandler = new DeleteHandler(entities, client);
		updateHandler = new UpdateHandler(client);

		// thread de MAJ de l'intervention via le serveur en "temps-reel"
		// TODO changer ça pour utiliser un système PUSH afin
		// que ce soit le serveur qui demande au client de se mettre à jour
		// et pas le client qui flood le serveur
		updateThread = new UpdateInterventionThread(this);
		updateThread.start();
	}
	
	@Override
	public boolean sendMessage(MessageDTO m) {
		MessageDTO mess = client.addMessage(interId, m);
		// FIXME check if this is ok (null thing)
		if (mess != null) return true;
		return false;
	}
	
	@Override
	public void addEntity(IEntity entity) {		
		addHandler.handle(entity);
		notifyObservers();
	}

	@Override
	public void removeEntity(IEntity entity) {
		delHandler.handle(entity);		
		notifyObservers();
	}

	@Override
	public void editEntity(IEntity entity) {
		updateHandler.handle(entity);
		notifyObservers();
	}
	
	@Override
	public void updateIntervention() {
		InterventionDTO remoteInter = client.getIntervention(interId);
		
		List<VehicleDTO> vehList = new ArrayList<VehicleDTO>(remoteInter.getVehicles());
		processUpdate(vehList, VehicleDTO.class);
		
		List<ActionDTO> actList = new ArrayList<ActionDTO>(remoteInter.getActions());
		processUpdate(actList, ActionDTO.class);
		
		List<TargetDTO> cibList = new ArrayList<TargetDTO>(remoteInter.getTargets());
		processUpdate(cibList, TargetDTO.class);
		
		List<VehicleDemandDTO> dMoyList = new ArrayList<VehicleDemandDTO>(remoteInter.getDemands());
		for (int i=0; i<dMoyList.size(); i++) {
			IEntity e = entities.find(dMoyList.get(i).getId(), VehicleDemandDTO.class);
			VehicleDemandDTO vd = dMoyList.get(i);
			// si la demande existe deja cote client
			if (e != null) {
				// on met à jour le model de son entitee
				e.setModel(vd);
				// on cherche à savoir si son état est "ACCEPTE"
				if (vd.getState() == DemandState.ACCEPTED) {
					// FIXME attention si l'état est ACCEPTED et que l'id du vehicule associe
					// n'a pas ete definie, ça va planter ! car il sera à -1
					
					// la demande a ete acceptee, il faut donc supprimer
					// la demande de la SITAC pour la remplacer par un vehicule
					ArrayList<VehicleDTO> vList = new ArrayList<VehicleDTO>(intervention.getVehicles());
					for (int k=0; k<vList.size(); k++) {
						// on cherche le model du véhicule associé à la demande
						if (vList.get(k).getId() == vd.getVehicleId()) {
							// on cree la future entitee du vehicule
							VehicleDTO v = vList.get(k);
							// on ajoute le nouveau vehicule aux entitées
							entities.add(EntityFactory.make(v));
							// on supprime la demande de vehicule
							entities.remove(vd);
						}
					}
				}
			// si la demande n'existe pas côté client, on l'ajoute
			} else {
				entities.add(EntityFactory.make(vd));
			}
		}
		
		List<VictimDTO> impList = new ArrayList<VictimDTO>(remoteInter.getVictims());
		processUpdate(impList, VictimDTO.class);
		
		listMessages = new ArrayList<MessageDTO>(remoteInter.getMessages());
		// TODO process messages differently
		
		List<SourceDTO> srcList = new ArrayList<SourceDTO>(remoteInter.getSources());
		processUpdate(srcList, SourceDTO.class);
		
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
		for (int i=0; i<list.size(); i++) {
			IEntity e = entities.find(list.get(i).getId(), aClass);
			// si l'entitee existe deja cote client
			if (e != null) {
				// on met à jour son model
				e.setModel(list.get(i));
				
			} else {
				// sinon on lui cree une entitee
				IModel model = list.get(i);
				entities.add(EntityFactory.make(model));
			}
		}
	}

	@Override
	public ArrayList<IEntity> getEntities() {
		return entities;
	}
	
	@Override
	public InterventionDTO getIntervention() {
		return intervention;
	}
	
	public List<MessageDTO> getListMessages() {
		return listMessages;
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

	@Override
	public int getInterventionId() {
		return interId;
	}
}
