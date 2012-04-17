package org.agetac.handler;

import javax.xml.transform.Source;

import org.agetac.common.client.AgetacClient;
import org.agetac.common.dto.ActionDTO;
import org.agetac.common.dto.SourceDTO;
import org.agetac.common.dto.TargetDTO;
import org.agetac.common.dto.VehicleDTO;
import org.agetac.common.dto.VehicleDemandDTO;
import org.agetac.common.dto.VictimDTO;
import org.agetac.entity.IEntity;

public class UpdateHandler implements IHandler {

	private AgetacClient client;
	
	public UpdateHandler(AgetacClient client) {
		this.client = client;
	}

	@Override
	public void handle(IEntity entity) {
		
		if (entity.getModel() instanceof ActionDTO) {
			ActionDTO a = (ActionDTO) entity.getModel();
			client.updateAction(a);
		
		} else if (entity.getModel() instanceof VehicleDTO) {
			VehicleDTO v = (VehicleDTO) entity.getModel();
			client.updateVehicle(v);
		
		} else if (entity.getModel() instanceof Source) {
			SourceDTO s = (SourceDTO) entity.getModel();
			client.updateSource(s);
		
		} else if (entity.getModel() instanceof TargetDTO) {
			TargetDTO t = (TargetDTO) entity.getModel();
			client.updateTarget(t);
		
		} else if (entity.getModel() instanceof VictimDTO) {
			VictimDTO v = (VictimDTO) entity.getModel();
			client.updateVictim(v);
			
		} else if (entity.getModel() instanceof VehicleDemandDTO) {
			VehicleDemandDTO vd = (VehicleDemandDTO) entity.getModel();
			client.updateVehicleDemand(vd);
		}
	}
}
