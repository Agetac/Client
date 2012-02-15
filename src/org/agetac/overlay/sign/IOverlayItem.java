package org.agetac.overlay.sign;

import org.agetac.entity.sign.IEntity;
import org.agetac.pictogram.sign.IPictogram;
import org.osmdroid.api.IGeoPoint;

public interface IOverlayItem {

	public IGeoPoint getGeoPoint();
	
	public IPictogram getPictogram();
	
	public IEntity getEntity();
	
	public boolean isCloseTo(IGeoPoint pt, int precision);
}
