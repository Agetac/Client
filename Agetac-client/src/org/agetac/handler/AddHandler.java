package org.agetac.handler;

import org.agetac.common.api.InterventionApi;
import org.agetac.common.exception.BadResponseException;
import org.agetac.common.model.impl.Action;
import org.agetac.common.model.impl.Cible;
import org.agetac.common.model.impl.DemandeMoyen;
import org.agetac.common.model.impl.Implique;
import org.agetac.common.model.impl.Source;
import org.agetac.common.model.impl.Vehicule;
import org.agetac.entity.EntityList;
import org.agetac.entity.IEntity;
import org.json.JSONException;

public class AddHandler implements IHandler {

	private EntityList entities;
	private InterventionApi iConn;
	
	public AddHandler(EntityList entList, InterventionApi iConn) {
		this.entities = entList;
		this.iConn = iConn;
	}

	@Override
	public void handle(IEntity entity) throws JSONException, BadResponseException {
		
		if (entity.getModel() instanceof Action) {
			Action a = (Action) entity.getModel();
			entity.setModel(iConn.putAction(a));
			entities.add(entity);
		
		} else if (entity.getModel() instanceof Vehicule) {
			Vehicule v = (Vehicule) entity.getModel();
			entity.setModel(iConn.postVehicule(v));
		
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
	}
}
