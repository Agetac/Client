package org.agetac.entity;


import java.util.Observable;
import java.util.Observer;

import org.agetac.common.dto.ActionDTO;
import org.agetac.common.dto.AgentDTO;
import org.agetac.common.dto.BarrackDTO;
import org.agetac.common.dto.GroupDTO;
import org.agetac.common.dto.IModel;
import org.agetac.common.dto.InterventionDTO;
import org.agetac.common.dto.PositionDTO;
import org.agetac.common.dto.SourceDTO;
import org.agetac.common.dto.TargetDTO;
import org.agetac.common.dto.VehicleDTO;
import org.agetac.common.dto.VehicleDemandDTO;
import org.agetac.common.dto.VictimDTO;
import org.agetac.view.IPictogram;
import org.agetac.view.LinePicto;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.graphics.Canvas;
import android.graphics.Point;

public class Entity implements IEntity, Observer {

	private static final String TAG = "Entity";
	private static final long serialVersionUID = 9102938L;
	private static final int EARTH_RADIUS_METERS = 6378137;

	/**
	 * MENU: entitée créée par defaut pour le menu
	 * OFF_SITAC: l'entitée a été créée par l'utilisateur mais n'a pas de position sur la SITAC
	 * ON_SITAC: entitée créée par l'utilisateur et placée sur la SITAC
	 */
	public enum EntityState {MENU, OFF_SITAC, ON_SITAC}
	
	private IModel model;
	private IPictogram picto;
	private IGeoPoint geoP;
	private EntityState state = EntityState.MENU;
	
	public Entity(IModel model, IPictogram picto) {
		this.model = model;
		this.picto = picto;
		if (model.getPosition() != null && model.getPosition().isKnown()) {
			int latE6 = model.getPosition().getLatitude();
			int longE6 = model.getPosition().getLongitude();
			this.geoP = new GeoPoint(latE6, longE6);
			this.state = EntityState.ON_SITAC;
			
		} else if (model.getPosition() != null && !model.getPosition().isKnown()) {
			this.state = EntityState.OFF_SITAC;
		
		} else {
			this.state = EntityState.MENU;
		}
		model.addObserver(this);
	}
	
	@Override
	public IGeoPoint getGeoPoint() {
		return geoP;
	}


	@Override
	public IModel getModel() {
		return model;
	}

	@Override
	public IPictogram getPictogram() {
		return picto;
	}

	@Override
	public IEntity clone() {
		if (model instanceof ActionDTO) {
			return new Entity(new ActionDTO(new PositionDTO(model.getPosition()), ((ActionDTO) model).getType(), new PositionDTO(((ActionDTO) model).getOrigin()), new PositionDTO(((ActionDTO) model).getAim())), picto.clone());
		} else if (model instanceof AgentDTO) {
			//humhum?
			//return new Entity(new AgentDTO(model.getId(), model.getId(), ((AgentDTO) model).getAptitude(), ((AgentDTO) model).getSubordonnes()), picto.clone());
		} else if (model instanceof BarrackDTO) {
			//humhum?
			//return new Entity(new ActionDTO(model.getId(), new PositionDTO(model.getPosition()), ((ActionDTO) model).getActionDTOType(), new PositionDTO(((ActionDTO) model).getOrigin()), new PositionDTO(((ActionDTO) model).getAim())), picto.clone());
		} else if (model instanceof TargetDTO) {
			return new Entity(new TargetDTO(new PositionDTO(model.getPosition()), ((TargetDTO) model).getType()), picto.clone());
		} else if (model instanceof VehicleDemandDTO) {
			return new Entity(new VehicleDemandDTO(model.getName(), new PositionDTO(model.getPosition()), ((VehicleDemandDTO) model).getState(), ((VehicleDemandDTO) model).getGroup()), picto.clone());
		} else if (model instanceof GroupDTO) {
			//humhum?
			//return new Entity(new ActionDTO(model.getId(), new PositionDTO(model.getPosition()), ((ActionDTO) model).getActionDTOType(), new PositionDTO(((ActionDTO) model).getOrigin()), new PositionDTO(((ActionDTO) model).getAim())), picto.clone());
		} else if (model instanceof VictimDTO) {
			//humhum?
			//return new Entity(new ActionDTO(model.getId(), new PositionDTO(model.getPosition()), ((ActionDTO) model).getActionDTOType(), new PositionDTO(((ActionDTO) model).getOrigin()), new PositionDTO(((ActionDTO) model).getAim())), picto.clone());
		} else if (model instanceof InterventionDTO) {
			//humhum?
			//return new Entity(new ActionDTO(model.getId(), new PositionDTO(model.getPosition()), ((ActionDTO) model).getActionDTOType(), new PositionDTO(((ActionDTO) model).getOrigin()), new PositionDTO(((ActionDTO) model).getAim())), picto.clone());
		} else if (model instanceof SourceDTO) {
			return new Entity(new SourceDTO(new PositionDTO(model.getPosition()), ((SourceDTO) model).getType()), picto.clone());
		} else if (model instanceof VehicleDTO) {
			//not sure of that
			return new Entity(new VehicleDTO(new PositionDTO(model.getPosition()), ((VehicleDTO) model).getType(), ((VehicleDTO) model).getBarrack().getName(), ((VehicleDTO) model).getState(), ((VehicleDTO) model).getGroup(), "Tsoin tsoin"), picto.clone());
		}
		return null;
	}

	@Override
	public EntityState getState() {
		return state;
	}

	@Override
	public boolean isCloseTo(IGeoPoint pt, int precision) {
		double distance = getDistanceInMeter(geoP, pt);
		return (distance <= precision);
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		Point p;
		p = mapView.getProjection().toMapPixels(geoP, null);
		
		// si c'est une action, on a quelques traitement à faire
		if (model instanceof ActionDTO) {
			ActionDTO actionModel = (ActionDTO) model;
			Point start, stop;

			IGeoPoint centerPos = new GeoPoint(actionModel.getPosition().getLatitude(), actionModel.getPosition().getLongitude());
			IGeoPoint originPos = new GeoPoint(actionModel.getOrigin().getLatitude(), actionModel.getOrigin().getLongitude());
			IGeoPoint aimPos = new GeoPoint(actionModel.getAim().getLatitude(), actionModel.getAim().getLongitude());
						
			start = mapView.getProjection().toMapPixels(originPos, null);
			Point centerPoint = mapView.getProjection().toPixels(centerPos, null);
			start.set(start.x-centerPoint.x, start.y-centerPoint.y);
			
			stop = mapView.getProjection().toMapPixels(aimPos, null);
			stop.set(stop.x-centerPoint.x, stop.y-centerPoint.y);
			
			LinePicto linePicto = (LinePicto) picto;
			linePicto.setStart(start);
			linePicto.setStop(stop);
			linePicto.setscaleRef(mapView.getProjection().metersToEquatorPixels(1.0f));
			
		}
		
		// on dessine le pictogramme
		picto.draw(canvas, p, shadow, mapView.getProjection());
	}

	@Override
	public void setModel(IModel model) {
		this.model = model;
		if (model.getPosition() != null && model.getPosition().isKnown()) {
			int latE6 = model.getPosition().getLatitude();
			int longE6 = model.getPosition().getLongitude();
			this.geoP = new GeoPoint(latE6, longE6);
			this.state = EntityState.ON_SITAC;
			
		} else if (model.getPosition() != null && !model.getPosition().isKnown()) {
			this.state = EntityState.OFF_SITAC;
		
		} else {
			this.state = EntityState.MENU;
		}
		this.model.addObserver(this);
	}
	
	/**
	 * Retourne la distance en mètre entre deux coordonnées GPS
	 * 
	 * @param src
	 *            une coordonnée GPS au format IGeoPoint
	 * @param dest
	 *            une coordonnée GPS au format IGeoPoint
	 * @return la distance en mètre entre ces deux coordonées
	 */
	public static double getDistanceInMeter(final IGeoPoint src, IGeoPoint dest) {
		double distance = 0;
		
        // premier point
        double srcLat = src.getLatitudeE6()*(1E-6);
        double srcLong = src.getLongitudeE6()*(1E-6);
        // deuxieme point
        double destLat = dest.getLatitudeE6()*(1E-6);
        double destLong = dest.getLongitudeE6()*(1E-6);

        // calcul des angles en radians
        double rlo1 = Math.toRadians(srcLong);
        double rla1 = Math.toRadians(srcLat);
        double rlo2 = Math.toRadians(destLong);
        double rla2 = Math.toRadians(destLat);

        double dlo = (rlo2 - rlo1) / 2;
        double dla = (rla2 - rla1) / 2;

        double angle = (Math.sin(dla) * Math.sin(dla)) + Math.cos(rla1)
                * Math.cos(rla2) * (Math.sin(dlo) * Math.sin(dlo));

        distance = EARTH_RADIUS_METERS
                * (2 * Math.atan2(Math.sqrt(angle), Math
                        .sqrt(1 - angle)));
		
		return distance;
	}
	
	@Override
	public String toString() {
		return "["+state.name()+"] "+model.toString();
	}

	@Override
	public void update(Observable observable, Object data) {
		IModel model = (IModel) data;
		if (model.getPosition().isKnown()) {
			int latE6 = model.getPosition().getLatitude();
			int longE6 = model.getPosition().getLongitude();
			this.geoP = new GeoPoint(latE6, longE6);
			this.state = EntityState.ON_SITAC;
			
		} else {
			this.state = EntityState.OFF_SITAC;
		}
	}
}
