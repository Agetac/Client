package org.agetac.handler;

import org.agetac.common.client.AgetacClient;
import org.agetac.common.dto.ActionDTO;
import org.agetac.common.dto.IModel;
import org.agetac.common.dto.SourceDTO;
import org.agetac.common.dto.TargetDTO;
import org.agetac.common.dto.VehicleDemandDTO;
import org.agetac.common.dto.VictimDTO;
import org.agetac.entity.EntityList;
import org.agetac.entity.IEntity;


public class AddHandler implements IHandler {

	private static final String TAG = "AddHandler";
	
	private AgetacClient client;
	private int interId;
	
	public AddHandler(AgetacClient client, int interId) {
		this.client = client;
		this.interId = interId;
	}

	@Override
	public void handle(IEntity entity) {
		IModel model = entity.getModel();
		
		if (model instanceof ActionDTO) {
			ActionDTO a = (ActionDTO) model;
			entity.setModel(client.addAction(interId, a));
		
		} else if (model instanceof SourceDTO) {
			SourceDTO s = (SourceDTO) model;
			entity.setModel(client.addSource(interId, s));
		
		} else if (model instanceof TargetDTO) {
			TargetDTO t = (TargetDTO) model;
			entity.setModel(client.addTarget(interId, t));
		
		} else if (model instanceof VictimDTO) {
			VictimDTO v = (VictimDTO) model;
			entity.setModel(client.addVictim(interId, v));
			
		} else if (model instanceof VehicleDemandDTO) {
			VehicleDemandDTO vd = (VehicleDemandDTO) model;
			entity.setModel(client.addVehicleDemand(interId, vd));
		}
	}
}
