package org.agetac.entity;

import org.agetac.common.Utils;
import org.agetac.common.model.sign.IModel;
import org.agetac.view.IPictogram;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.graphics.Canvas;
import android.graphics.Point;

public class Entity<T extends IModel> implements IEntity {

	private static final long serialVersionUID = 9102938L;

	public enum EntityState {ON_SITAC, OFF_SITAC}
	
	private T model;
	private IPictogram picto;
	private IGeoPoint geoP;
	private EntityState state;
	
	public Entity(T model, IPictogram picto, EntityState state) {
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
		return new Entity<T>(model, picto, state);
	}

	@Override
	public EntityState getState() {
		return state;
	}

	public void setState(EntityState state) {
		this.state = state;
	}

	@Override
	public boolean isCloseTo(IGeoPoint pt, int precision) {
		double distance = Utils.getDistanceInMeter(geoP, pt);
		return (distance <= precision);
	}

	@Override
	public void draw(Canvas canvas, MapView mapV, boolean shadow) {
		Point p;
		p = mapV.getProjection().toMapPixels(geoP, null);
		picto.draw(canvas, p, shadow, mapV.getProjection());
	}
}
