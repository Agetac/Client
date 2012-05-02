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

public class DeleteHandler implements IHandler {

	private EntityList entities;
	private AgetacClient client;
	
	public DeleteHandler(EntityList entList, AgetacClient client) {
		this.entities = entList;
		this.client = client;
	}

	@Override
	public void handle(IEntity entity) {
		IModel model = entity.getModel();
		
		if (model instanceof VehicleDTO) {
			VehicleDTO v = (VehicleDTO) model;
			client.deleteVehicle(v.getId());
			entities.remove(entity);
		
		} else if (model instanceof ActionDTO) {
			ActionDTO a = (ActionDTO) model;
			client.deleteAction(a.getId());
			entities.remove(entity);
		
		} else if (model instanceof SourceDTO) {
			SourceDTO s = (SourceDTO) model;
			client.deleteSource(s.getId());
			entities.remove(entity);
		
		} else if (model instanceof TargetDTO) {
			TargetDTO c = (TargetDTO) model;
			client.deleteTarget(c.getId());
			entities.remove(entity);
		
		} else if (model instanceof VictimDTO) {
			VictimDTO i = (VictimDTO) model;
			client.deleteVictim(i.getId());
			entities.remove(entity);
		
		} else if (model instanceof VehicleDemandDTO) {
			VehicleDemandDTO dm = (VehicleDemandDTO) model;
			client.deleteVehicleDemand(dm.getId());
			entities.remove(entity);
		}
	}
}
