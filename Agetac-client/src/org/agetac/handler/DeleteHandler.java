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

public class DeleteHandler implements IHandler {

	private EntityList entities;
	private InterventionApi iConn;
	
	public DeleteHandler(EntityList entList, InterventionApi iConn) {
		this.entities = entList;
		this.iConn = iConn;
	}

	@Override
	public void handle(IEntity entity) throws JSONException, BadResponseException {
		
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
	}
}
