package org.agetac.entity.impl;

import org.agetac.entity.sign.IEntity;
import org.agetac.model.impl.Vehicule;
import org.agetac.model.sign.IModel;
import org.agetac.pictogram.sign.IPictogram;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

public class VehiculeEntity implements IEntity {

	private static final long serialVersionUID = 1L;
	
	private Vehicule model;
	private IGeoPoint geoP;
	private IPictogram pictogram;
	
	public VehiculeEntity(Vehicule model, IPictogram picto) {
		this.model = model;
		this.pictogram = picto;
		int latE6 = (int) model.getPosition().getLatitude();
		int longE6 = (int) model.getPosition().getLongitude();
		this.geoP = new GeoPoint(latE6, longE6);
	}
	
	@Override
	public IModel getModel() {
		return model;
	}
	
	@Override
	public IGeoPoint getGeoPoint() {
		return geoP;
	}

	@Override
	public IPictogram getPictogram() {
		return pictogram;
	}
}
