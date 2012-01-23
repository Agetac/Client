package org.agetac.overlay;

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
	public boolean isCloseTo(IGeoPoint geoP) {
		boolean ret = false;
		
		// TODO permettre à l'utilisateur de modifier la précision
		// TODO via les paramètres de l'appli
//		int tolerance = 5;
//		
//		int lat1, lat2, lat3, lat4;
//		int long1, long2, long3, long4;
//		lat1 = latitude-5;
//		long1 = longitude-5;
//		lat2 = latitude+5;
//		long2 = longitude-5;
//		lat3 = latitude-5;
//		long3 = longitude+5;
//		lat4 = latitude+5;
//		long4 = longitude+5;
		
		
		
		return ret;
	}
}
