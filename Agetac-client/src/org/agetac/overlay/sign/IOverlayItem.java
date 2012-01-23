package org.agetac.overlay.sign;

import org.agetac.pictogram.sign.IPictogram;
import org.osmdroid.api.IGeoPoint;

public interface IOverlayItem {

	public IGeoPoint getGeoPoint();
	
	public IPictogram getPictogram();

	public boolean isCloseTo(IGeoPoint geoP);
}
