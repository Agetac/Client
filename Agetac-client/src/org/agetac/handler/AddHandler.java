package org.agetac.handler;

import org.agetac.common.client.AgetacClient;
import org.agetac.common.dto.ActionDTO;
import org.agetac.common.dto.IModel;
import org.agetac.common.dto.SourceDTO;
import org.agetac.common.dto.TargetDTO;
import org.agetac.common.dto.VehicleDTO;
import org.agetac.common.dto.VehicleDemandDTO;
import org.agetac.common.dto.VictimDTO;
import org.agetac.entity.EntityList;
import org.agetac.entity.IEntity;


public class AddHandler implements IHandler {

	private static final String TAG = "AddHandler";
	
	private EntityList entities;
	private AgetacClient client;
	private int interId;
	
	public AddHandler(EntityList entList, AgetacClient client, int interId) {
		this.entities = entList;
		this.client = client;
		this.interId = interId;
	}

	@Override
	public void handle(IEntity entity) {
		IModel model = entity.getModel();
		
		if (model instanceof ActionDTO) {
			ActionDTO a = (ActionDTO) model;
			entity.setModel(client.addAction(interId, a));
			entities.add(entity);
		
		} else if (model instanceof VehicleDTO) {
			VehicleDTO v = (VehicleDTO) model;
			// FIXME le client n'est pas sensé créer des véhicules...
			android.util.Log.d(TAG, "/!\\ trying to add a vehicle directly from the client (you are wrong, it does nothing !)");
		
		} else if (model instanceof SourceDTO) {
			SourceDTO s = (SourceDTO) model;
			entity.setModel(client.addSource(interId, s));
			entities.add(entity);
		
		} else if (model instanceof TargetDTO) {
			TargetDTO t = (TargetDTO) model;
			entity.setModel(client.addTarget(interId, t));
			entities.add(entity);
		
		} else if (model instanceof VictimDTO) {
			VictimDTO v = (VictimDTO) model;
			entity.setModel(client.addVictim(interId, v));
			entities.add(entity);
			
		} else if (model instanceof VehicleDemandDTO) {
			VehicleDemandDTO vd = (VehicleDemandDTO) model;
			entity.setModel(client.addVehicleDemand(interId, vd));
			entities.add(entity);
		}
	}
}
