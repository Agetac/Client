package org.agetac.entity;

import java.util.ArrayList;

import org.agetac.common.exception.InvalidJSONException;
import org.agetac.common.model.impl.*;
import org.agetac.common.model.impl.Action.ActionType;
import org.agetac.common.model.impl.DemandeMoyen.EtatDemande;
import org.agetac.common.model.sign.IModel;
import org.agetac.view.IPictogram;
import org.json.JSONException;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.graphics.Canvas;
import android.graphics.Point;

public class Entity implements IEntity {

	private static final long serialVersionUID = 9102938L;
	private static final int EARTH_RADIUS_METERS = 6378137;

	public enum EntityState {ON_SITAC, OFF_SITAC}
	
	private IModel model;
	private IPictogram picto;
	private IGeoPoint geoP;
	private EntityState state;
	
	public Entity(IModel model, IPictogram picto, EntityState state) {
		this.model = model;
		this.picto = picto;
		this.state = state;
		int latE6 = (int) model.getPosition().getLatitude();
		int longE6 = (int) model.getPosition().getLongitude();
		this.geoP = new GeoPoint(latE6, longE6);
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
		if (model instanceof Action) {
			return new Entity(new Action(model.getUniqueID(), new Position(model.getPosition()), ((Action) model).getActionType(), new Position(((Action) model).getOrigin()), new Position(((Action) model).getAim())), picto.clone(), state);
		} else if (model instanceof Agent) {
			//humhum?
			//return new Entity(new Agent(model.getUniqueID(), model.getUniqueID(), ((Agent) model).getAptitude(), ((Agent) model).getSubordonnes()), picto.clone(), state);
		} else if (model instanceof Caserne) {
			//humhum?
			//return new Entity(new Action(model.getUniqueID(), new Position(model.getPosition()), ((Action) model).getActionType(), new Position(((Action) model).getOrigin()), new Position(((Action) model).getAim())), picto.clone(), state);
		} else if (model instanceof Cible) {
			return new Entity(new Cible(model.getUniqueID(), new Position(model.getPosition())), picto.clone(), state);
		} else if (model instanceof DemandeMoyen) {
			return new Entity(new DemandeMoyen(model.getUniqueID(), model.getName(), new Position(model.getPosition()), ((DemandeMoyen) model).getEtat(), ((DemandeMoyen) model).getGroupe()), picto.clone(), state);
		} else if (model instanceof Groupe) {
			//humhum?
			//return new Entity(new Action(model.getUniqueID(), new Position(model.getPosition()), ((Action) model).getActionType(), new Position(((Action) model).getOrigin()), new Position(((Action) model).getAim())), picto.clone(), state);
		} else if (model instanceof Implique) {
			//humhum?
			//return new Entity(new Action(model.getUniqueID(), new Position(model.getPosition()), ((Action) model).getActionType(), new Position(((Action) model).getOrigin()), new Position(((Action) model).getAim())), picto.clone(), state);
		} else if (model instanceof Intervention) {
			//humhum?
			//return new Entity(new Action(model.getUniqueID(), new Position(model.getPosition()), ((Action) model).getActionType(), new Position(((Action) model).getOrigin()), new Position(((Action) model).getAim())), picto.clone(), state);
		} else if (model instanceof Source) {
			return new Entity(new Source(model.getUniqueID(), new Position(model.getPosition())), picto.clone(), state);
		} else if (model instanceof Vehicule) {
			//humhum?
			//return new Entity(new Action(model.getUniqueID(), new Position(model.getPosition()), ((Action) model).getActionType(), new Position(((Action) model).getOrigin()), new Position(((Action) model).getAim())), picto.clone(), state);
		}
		System.err.println("OULALALA!");
		return null;
	}

	@Override
	public EntityState getState() {
		return state;
	}
	
	@Override
	public void setState(EntityState state) {
		this.state = state;
	}

	@Override
	public boolean isCloseTo(IGeoPoint pt, int precision) {
		double distance = getDistanceInMeter(geoP, pt);
		return (distance <= precision);
	}

	@Override
	public void draw(Canvas canvas, MapView mapV, boolean shadow) {
		Point p;
		p = mapV.getProjection().toMapPixels(geoP, null);
		//android.util.Log.d("ENTITIII",p.toString()+geoP.toString());
		picto.draw(canvas, p, shadow, mapV.getProjection());
	}

	@Override
	public void setModel(IModel model) {
		this.model = model;
		int latE6 = (int) model.getPosition().getLatitude();
		int longE6 = (int) model.getPosition().getLongitude();
		this.geoP = new GeoPoint(latE6, longE6);	
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
		return model.toString();
	}
}
