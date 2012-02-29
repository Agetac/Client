package org.agetac.entity.sign;

import java.io.Serializable;

import org.agetac.entity.impl.Entity.EntityState;
import org.agetac.model.sign.IModel;
import org.agetac.pictogram.sign.IPictogram;
import org.osmdroid.api.IGeoPoint;

public interface IEntity extends Serializable {
	
	public IModel getModel();

	public IPictogram getPictogram();

	public IGeoPoint getGeoPoint();
	
	public boolean isCloseTo(IGeoPoint pt, int precision);
	
	public IEntity clone();

	public EntityState getState();
}
