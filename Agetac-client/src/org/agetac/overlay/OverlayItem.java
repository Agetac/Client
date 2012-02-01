package org.agetac.overlay;

import org.agetac.common.Utils;
import org.agetac.overlay.sign.IOverlayItem;
import org.agetac.pictogram.sign.IPictogram;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

public class OverlayItem implements IOverlayItem {

	private int latitude;
	private int longitude;
	private IGeoPoint geoP;
	private IPictogram picto;
	
	public OverlayItem(IPictogram picto, final int latE6, final int longE6) {
		this.latitude = latE6;
		this.longitude = longE6;
		this.picto = picto;
		defineGeoPoint();
	}

	public OverlayItem(IPictogram picto, final double lat, final double lon) {
		this.latitude = (int) (lat * 1E6);
		this.longitude = (int) (lon * 1E6);
		this.picto = picto;
		defineGeoPoint();
	}
	
	private void defineGeoPoint() {
		 this.geoP = new GeoPoint(latitude, longitude);
	}
	
	public IGeoPoint getGeoPoint() {
		return geoP;
	}

	@Override
	public IPictogram getPictogram() {
		return picto;
	}

	@Override
	public boolean isCloseTo(IGeoPoint pt, int precision) {
		double distance = Utils.getDistanceInMeter(geoP, pt);
		if (distance <= precision) return true;
		return false;
	}
}
