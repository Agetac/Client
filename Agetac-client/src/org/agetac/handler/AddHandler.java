package org.agetac.handler;

import javax.xml.transform.Source;

import org.agetac.common.client.AgetacClient;
import org.agetac.common.dto.ActionDTO;
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
		
		if (entity.getModel() instanceof ActionDTO) {
			ActionDTO a = (ActionDTO) entity.getModel();
			entity.setModel(client.addAction(interId, a));
			entities.add(entity);
		
		} else if (entity.getModel() instanceof VehicleDTO) {
			VehicleDTO v = (VehicleDTO) entity.getModel();
			// FIXME le client n'est pas sensé créer des véhicules...
			android.util.Log.d(TAG, "/!\\ trying to add a vehicle directly from the client (you are wrong, it does nothing !)");
		
		} else if (entity.getModel() instanceof Source) {
			SourceDTO s = (SourceDTO) entity.getModel();
			entity.setModel(client.addSource(interId, s));
			entities.add(entity);
		
		} else if (entity.getModel() instanceof TargetDTO) {
			TargetDTO t = (TargetDTO) entity.getModel();
			entity.setModel(client.addTarget(interId, t));
			entities.add(entity);
		
		} else if (entity.getModel() instanceof VictimDTO) {
			VictimDTO v = (VictimDTO) entity.getModel();
			entity.setModel(client.addVictim(interId, v));
			entities.add(entity);
			
		} else if (entity.getModel() instanceof VehicleDemandDTO) {
			VehicleDemandDTO vd = (VehicleDemandDTO) entity.getModel();
			entity.setModel(client.addVehicleDemand(interId, vd));
			entities.add(entity);
		}
	}
}
