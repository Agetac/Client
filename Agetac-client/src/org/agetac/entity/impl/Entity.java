package org.agetac.entity.impl;

import org.agetac.entity.sign.IEntity;
import org.agetac.model.sign.IModel;
import org.agetac.pictogram.sign.IPictogram;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

public class Entity<T extends IModel> implements IEntity {

	private static final long serialVersionUID = 9102938L;
	
	private T model;
	private IPictogram picto;
	private IGeoPoint geoP;
	
	public Entity(T model, IPictogram picto) {
		this.model = model;
		this.picto = picto;
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
		return new Entity<T>(model, picto);
	}
}
