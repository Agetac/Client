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
import org.agetac.common.model.impl.Implique;
import org.agetac.common.model.impl.Intervention;
import org.agetac.common.model.impl.Message;
import org.agetac.common.model.impl.Source;
import org.agetac.common.model.impl.Vehicule;
import org.agetac.entity.EntityList;
import org.agetac.entity.IEntity;
import org.agetac.entity.Entity.EntityState;
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
			updateThread = new UpdateInterventionThread(this, iConn);
			//updateThread.start();
			
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
		//entities.add(entity);
		
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
				System.out.println(">>> "+dm.toString());
				DemandeMoyen dm2 = iConn.putDemandeMoyen(dm);
				System.out.println(">>>>>>> "+dm2.toString());
				entity.setModel(dm2);
				entities.add(entity);
			}
			
		} catch (BadResponseException e) {
			android.util.Log.e(TAG, "addEntity BadResponse: "+e.getMessage());
			
		} catch (JSONException e) {
			android.util.Log.e(TAG, "addEntity JSONException: "+e.getMessage());
		}
		System.out.println("YOUPI TRAlALALALA");
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
		for (int i=0; i<vehList.size(); i++) {
			for (int j=0; j<entities.size(); j++) {
				IEntity e = entities.find(vehList.get(i).getUniqueID(), Vehicule.class);
				// si le vehicule existe deja cote client
				if (e != null) {
					// on met à jour le model de son entitee
					e.setModel(vehList.get(i));
					break;
				} else {
					IEntity ent;
					Vehicule model = vehList.get(i);
					
					// on cree une entite pour le représenter
					switch (model.getCategorie()) {
						case FPT:
							ent = holder.getEntity(EntityHolder.GREEN_COL).clone();
							ent.setState(EntityState.ON_SITAC);
							break;
							
						case VSAV:
							ent = holder.getEntity(EntityHolder.GREEN_ISOLE).clone();
							break;
							
						// TODO prendre en compte les autres cas
							
						default:
							// TODO creer une NullEntity ?
							ent = holder.getEntity(EntityHolder.RED_ISOLE).clone();
							break;
					}
					
					ent.setModel(model);
					entities.add(ent);
				}
			}
		}
		
		List<Action> actList = inter.getActions();
		for (int i=0; i<actList.size(); i++) {
			android.util.Log.d(TAG, "act > "+actList.get(i).toString());
		}
		
		List<Cible> cibList = inter.getCibles();
		for (int i=0; i<cibList.size(); i++) {
			android.util.Log.d(TAG, "cib > "+cibList.get(i).toString());
		}
		
		List<DemandeMoyen> dMoyList = inter.getDemandesMoyen();
		for (int i=0; i<dMoyList.size(); i++) {
			android.util.Log.d(TAG, "demMoy > "+dMoyList.get(i).toString());
		}
		
		List<Implique> impList = inter.getImpliques();
		for (int i=0; i<impList.size(); i++) {
			android.util.Log.d(TAG, "imp > "+impList.get(i).toString());
		}
		
		List<Message> messList = inter.getMessages();
		for (int i=0; i<messList.size(); i++) {
			android.util.Log.d(TAG, "mess > "+messList.get(i).toString());
		}
		
		List<Source> srcList = inter.getSources();
		for (int i=0; i<srcList.size(); i++) {
//			for (int j=0; j<entities.size(); j++) {
//				// si le vehicule existe deja cote client
//				if (srcList.get(i).getUniqueID() == entities.get(j).getModel().getUniqueID()) {
//					// on met à jour le model de son entitee
//					entities.get(j).setModel(srcList.get(i));
//					
//				// si le vehicule est nouveau
//				} else {
//					IEntity ent;
//					Source model = srcList.get(i);
//					
//					// on cree une entite pour le représenter
//					switch (model.ge) {
//						case FPT:
//							ent = holder.getEntity(EntityHolder.RED_SINGLE).clone();
//							break;
//							
//						case VSAV:
//							ent = holder.getEntity(EntityHolder.GREEN_SINGLE).clone();
//							break;
//							
//						// TODO prendre en compte les autres cas
//							
//						default:
//							// TODO creer une NullEntity ?
//							ent = holder.getEntity(EntityHolder.RED_SINGLE).clone();
//							break;
//					}
//					
//					ent.setModel(model);
//					entities.add(ent);
//				}
//			}
		}
		
		notifyObservers();
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

	@Override
	public void stopUpdates() {
		if (updateThread != null) updateThread.doStop();
	}
}
