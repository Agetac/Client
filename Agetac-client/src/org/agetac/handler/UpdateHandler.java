package org.agetac.handler;

import javax.xml.transform.Source;

import org.agetac.common.client.AgetacClient;
import org.agetac.common.dto.ActionDTO;
import org.agetac.common.dto.IModel;
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
		IModel model = entity.getModel();
		
		if (model instanceof ActionDTO) {
			ActionDTO a = (ActionDTO) model;
			client.updateAction(a);
		
		} else if (model instanceof VehicleDTO) {
			VehicleDTO v = (VehicleDTO) model;
			client.updateVehicle(v);
		
		} else if (model instanceof SourceDTO) {
			SourceDTO s = (SourceDTO) model;
			client.updateSource(s);
		
		} else if (model instanceof TargetDTO) {
			TargetDTO t = (TargetDTO) model;
			client.updateTarget(t);
		
		} else if (model instanceof VictimDTO) {
			VictimDTO v = (VictimDTO) model;
			client.updateVictim(v);
			
		} else if (model instanceof VehicleDemandDTO) {
			VehicleDemandDTO vd = (VehicleDemandDTO) model;
			client.updateVehicleDemand(vd);
		}
	}
}
