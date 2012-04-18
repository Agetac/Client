package org.agetac.entity;

import org.agetac.common.dto.ActionDTO;
import org.agetac.common.dto.AgentDTO;
import org.agetac.common.dto.BarrackDTO;
import org.agetac.common.dto.GroupDTO;
import org.agetac.common.dto.IModel;
import org.agetac.common.dto.PositionDTO;
import org.agetac.common.dto.SourceDTO;
import org.agetac.common.dto.TargetDTO;
import org.agetac.common.dto.VehicleDTO;
import org.agetac.common.dto.VehicleDemandDTO;
import org.agetac.common.dto.VictimDTO;
import org.agetac.entity.Entity.EntityState;

public class EntityFactory {
	
	private static final String TAG = "EntityFactory";
		
	public static IEntity make(IEntity e) {
		IModel model = e.getModel();
		if (e.getModel() instanceof ActionDTO) {
			return new Entity(new ActionDTO(new PositionDTO(model.getPosition()), ((ActionDTO) model).getType(), new PositionDTO(((ActionDTO) model).getOrigin()), new PositionDTO(((ActionDTO) model).getAim())), e.getPictogram().clone(), e.getState());
		} else if (model instanceof AgentDTO) {
			return e;
		} else if (model instanceof BarrackDTO) {
			return e;
		} else if (model instanceof TargetDTO) {
			return new Entity(new TargetDTO(new PositionDTO(model.getPosition()), ((TargetDTO) model).getType()), e.getPictogram().clone(), e.getState());
		} else if (model instanceof VehicleDemandDTO) {
			return new Entity(new VehicleDemandDTO(model.getName(), new PositionDTO(model.getPosition()), ((VehicleDemandDTO) model).getState(), ((VehicleDemandDTO) model).getGroup()), e.getPictogram().clone(), e.getState());
		} else if (model instanceof GroupDTO) {
			return e;
		} else if (model instanceof VictimDTO) {
			return e;
		} else if (model instanceof SourceDTO) {
			return new Entity(new SourceDTO(new PositionDTO(model.getPosition()), ((SourceDTO) model).getType()), e.getPictogram().clone(), e.getState());
		} else if (model instanceof VehicleDTO) {
			return e;		
		}
		System.out.println("Warning strange Entity with model -> "+e.getModel().toString());
		return e;
	}
	
	public static IEntity make(IModel model) {
		// default entity
		IEntity ent = make(EntityHolder.getEntity(EntityHolder.UNKNOWN));
		
		if (model instanceof VehicleDTO) {
			switch (((VehicleDTO) model).getType()) {
				case FPT:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_ISOLE));
					break;
					
				case VSAV:
					ent = make(EntityHolder.getEntity(EntityHolder.GREEN_ISOLE));
					break;
					
				// TODO prendre en compte les autres cas
			}
			
		} else if (model instanceof SourceDTO) {
			switch (((SourceDTO) model).getType()) {
				case CHEM:
					ent = make(EntityHolder.getEntity(EntityHolder.ORANGE_UP));
					break;
					
				case WATER:
					ent = make(EntityHolder.getEntity(EntityHolder.BLUE_UP));
					break;
					
				case FIRE:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_UP));
					break;
			}
			
		} else if (model instanceof TargetDTO) {
			switch (((TargetDTO) model).getType()) {
				case CHEM:
					ent = make(EntityHolder.getEntity(EntityHolder.ORANGE_DOWN));
					break;
					
				case WATER:
					ent = make(EntityHolder.getEntity(EntityHolder.BLUE_DOWN));
					break;
					
				case FIRE:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_DOWN));
					break;
					
				case HUMAN:
					ent = make(EntityHolder.getEntity(EntityHolder.GREEN_DOWN));
					break;
			}
			
		} else if (model instanceof VehicleDemandDTO) {
			switch (((VehicleDemandDTO) model).getType()) {
				case FPT:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_ISOLE));
					break;
					
				case VSAV:
					ent = make(EntityHolder.getEntity(EntityHolder.GREEN_ISOLE));
					break;
				
			// TODO prendre en compte les autres cas
			}
			
		} else if (model instanceof ActionDTO) {
			switch (((ActionDTO) model).getType()) {
				case FIRE:
					
					break;
					
				case HUMAN:
					
					break;
					
				case WATER:
					
					break;
			}
		}
		
		if (model.getPosition() == null || !model.getPosition().isKnown()) {
			// alors l'item n'a pas de position definie et donc son etat est OFF_SITAC
			ent.setState(EntityState.OFF_SITAC);
			android.util.Log.d(TAG, "he says we are OFF SITAC");
		
		} else {
			ent.setState(EntityState.ON_SITAC);
			android.util.Log.d(TAG, "IM ON SITAC DUDE !");
		}
		
		ent.setModel(model);
		
		return ent;
	}

}
