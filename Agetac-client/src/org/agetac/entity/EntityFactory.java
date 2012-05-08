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
import org.agetac.view.IPictogram;
import org.agetac.view.LinePicto;

public class EntityFactory {
	
	private static final String TAG = "EntityFactory";
		
	public static IEntity make(IEntity e) {
		IModel model = e.getModel();
		if (e.getModel() instanceof ActionDTO) {		
			return new Entity(new ActionDTO(new PositionDTO(model.getPosition()), ((ActionDTO) model).getType(), new PositionDTO(((ActionDTO) model).getOrigin()), new PositionDTO(((ActionDTO) model).getAim())), e.getPictogram().clone());
		} else if (model instanceof AgentDTO) {
			return e;
		} else if (model instanceof BarrackDTO) {
			return e;
		} else if (model instanceof TargetDTO) {
			return new Entity(new TargetDTO(new PositionDTO(model.getPosition()), ((TargetDTO) model).getType()), e.getPictogram().clone());
		} else if (model instanceof VehicleDemandDTO) {
			return new Entity(new VehicleDemandDTO(model.getName(), new PositionDTO(model.getPosition()), ((VehicleDemandDTO) model).getState(), ((VehicleDemandDTO) model).getGroup()), e.getPictogram().clone());
		} else if (model instanceof GroupDTO) {
			return e;
		} else if (model instanceof VictimDTO) {
			return e;
		} else if (model instanceof SourceDTO) {
			return new Entity(new SourceDTO(new PositionDTO(model.getPosition()), ((SourceDTO) model).getType()), e.getPictogram().clone());
		} else if (model instanceof VehicleDTO) {
			return e;		
		}
		System.out.println("Warning strange Entity with model -> "+e.getModel().toString());
		return e;
	}
	
	public static IEntity make(IModel model) {
		// default entity
		IEntity ent = EntityHolder.getUnknownEntity();
		
		if (model instanceof VehicleDTO) {
			switch (((VehicleDTO) model).getType()) {
				case FPT:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
					break;
					
				case VSAV:
					ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
					break;

				case BEA:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
					break;
					
				case BLS:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case BLSP:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case BRS:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case CAEM:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
					break;
					
				case CCFM:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
					break;
					
				case CCGC:
					ent = make(EntityHolder.getEntity(EntityHolder.BLUE_AGRES));
					break;
					
				case CCGCLC:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
					break;
					
				case DA:
					ent = make(EntityHolder.getEntity(EntityHolder.BLUE_AGRES));
					break;
					
				case EMB:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case EPS:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
					break;
					
				case ESPM:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case FMOGP:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
					break;
					
				case MPR:
					ent = make(EntityHolder.getEntity(EntityHolder.BLUE_AGRES));
					break;
					
				case PCM:
					ent = make(EntityHolder.getEntity(EntityHolder.VIOLET_AGRES));
					break;
					
				case PEVSD:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
					break;
					
				case SAC_PS:
					ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
					break;
					
				case UTP:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case VAR:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
					break;
					
				case VCYNO:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case VICB:
					ent = make(EntityHolder.getEntity(EntityHolder.ORANGE_AGRES));
					break;
					
				case VL:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case VLCC:
					ent = make(EntityHolder.getEntity(EntityHolder.VIOLET_AGRES));
					break;
					
				case VLCG:
					ent = make(EntityHolder.getEntity(EntityHolder.VIOLET_AGRES));
					break;
					
				case VLCGD:
					ent = make(EntityHolder.getEntity(EntityHolder.VIOLET_AGRES));
					break;
					
				case VLCS:
					ent = make(EntityHolder.getEntity(EntityHolder.VIOLET_AGRES));
					break;
					
				case VLDP:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case VLHR:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case VLOS:
					ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
					break;
					
				case VLS:
					ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
					break;
					
				case VLSV:
					ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
					break;
					
				case VNRBC:
					ent = make(EntityHolder.getEntity(EntityHolder.ORANGE_AGRES));
					break;
					
				case VPHV:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case VPL:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case VPRO:
					ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
					break;
					
				case VRAD:
					ent = make(EntityHolder.getEntity(EntityHolder.ORANGE_AGRES));
					break;
					
				case VRCB:
					ent = make(EntityHolder.getEntity(EntityHolder.ORANGE_AGRES));
					break;
					
				case VSM:
					ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
					break;
					
				case VSR:
					ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
					break;
					
				case VTP:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
					
				case VTU:
					ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
					break;
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
			// FIXME les demandes devraient être affichées avec des tirets et non avec les pictos des vehicules
			case FPT:
				ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
				break;
				
			case VSAV:
				ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
				break;

			case BEA:
				ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
				break;
				
			case BLS:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case BLSP:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case BRS:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case CAEM:
				ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
				break;
				
			case CCFM:
				ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
				break;
				
			case CCGC:
				ent = make(EntityHolder.getEntity(EntityHolder.BLUE_AGRES));
				break;
				
			case CCGCLC:
				ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
				break;
				
			case DA:
				ent = make(EntityHolder.getEntity(EntityHolder.BLUE_AGRES));
				break;
				
			case EMB:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case EPS:
				ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
				break;
				
			case ESPM:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case FMOGP:
				ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
				break;
				
			case MPR:
				ent = make(EntityHolder.getEntity(EntityHolder.BLUE_AGRES));
				break;
				
			case PCM:
				ent = make(EntityHolder.getEntity(EntityHolder.VIOLET_AGRES));
				break;
				
			case PEVSD:
				ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
				break;
				
			case SAC_PS:
				ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
				break;
				
			case UTP:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case VAR:
				ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
				break;
				
			case VCYNO:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case VICB:
				ent = make(EntityHolder.getEntity(EntityHolder.ORANGE_AGRES));
				break;
				
			case VL:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case VLCC:
				ent = make(EntityHolder.getEntity(EntityHolder.VIOLET_AGRES));
				break;
				
			case VLCG:
				ent = make(EntityHolder.getEntity(EntityHolder.VIOLET_AGRES));
				break;
				
			case VLCGD:
				ent = make(EntityHolder.getEntity(EntityHolder.VIOLET_AGRES));
				break;
				
			case VLCS:
				ent = make(EntityHolder.getEntity(EntityHolder.VIOLET_AGRES));
				break;
				
			case VLDP:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case VLHR:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case VLOS:
				ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
				break;
				
			case VLS:
				ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
				break;
				
			case VLSV:
				ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
				break;
				
			case VNRBC:
				ent = make(EntityHolder.getEntity(EntityHolder.ORANGE_AGRES));
				break;
				
			case VPHV:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case VPL:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case VPRO:
				ent = make(EntityHolder.getEntity(EntityHolder.RED_AGRES));
				break;
				
			case VRAD:
				ent = make(EntityHolder.getEntity(EntityHolder.ORANGE_AGRES));
				break;
				
			case VRCB:
				ent = make(EntityHolder.getEntity(EntityHolder.ORANGE_AGRES));
				break;
				
			case VSM:
				ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
				break;
				
			case VSR:
				ent = make(EntityHolder.getEntity(EntityHolder.GREEN_AGRES));
				break;
				
			case VTP:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
				
			case VTU:
				ent = make(EntityHolder.getEntity(EntityHolder.BLACK_AGRES));
				break;
			}
			
		} else if (model instanceof ActionDTO) {
			switch (((ActionDTO) model).getType()) {
				case FIRE:
					ent = make(EntityHolder.getEntity(EntityHolder.LINE_RED));
					break;
					
				case HUMAN:
					ent = make(EntityHolder.getEntity(EntityHolder.LINE_GREEN));
					break;
					
				case WATER:
					ent = make(EntityHolder.getEntity(EntityHolder.LINE_BLUE));
					break;
					
				case CHEM:
					ent = make(EntityHolder.getEntity(EntityHolder.LINE_ORANGE));
					break;
			}
			
		}
		
		ent.setModel(model);
		
		return ent;
	}

}
