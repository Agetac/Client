package org.agetac.model.sign;

import org.agetac.pictogram.sign.IPictogram;
import org.osmdroid.util.GeoPoint;

public interface IEntity {

	public String getName();
	
	public boolean isDeBase();
	
	public GeoPoint getLocation();
	
	public IPictogram getPicto();
}
