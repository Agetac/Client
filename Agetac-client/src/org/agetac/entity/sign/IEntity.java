package org.agetac.entity.sign;

import java.io.Serializable;

import org.agetac.model.sign.IModel;
import org.agetac.pictogram.sign.IPictogram;
import org.osmdroid.api.IGeoPoint;

public interface IEntity extends Serializable {
	
	public IModel getModel();

	public IPictogram getPictogram();

	public IGeoPoint getGeoPoint();
}
