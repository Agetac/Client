package org.agetac.common;

import org.osmdroid.api.IGeoPoint;

public class Utils {

	private static final int EARTH_RADIUS_METERS = 6378000;
	private static final double PI = Math.PI;

	/**
	 * Retourne la distance en mètre entre deux coordonnées GPS
	 * 
	 * @param gp0
	 *            une coordonnée GPS au format IGeoPoint
	 * @param gp1
	 *            une coordonnée GPS au format IGeoPoint
	 * @return la distance en mètre entre ces deux coordonées
	 */
	public static double getDistanceInMeter(IGeoPoint src, IGeoPoint dest) {
		double distance = 0;
		int sourceLat = src.getLatitudeE6();
		int sourceLong = src.getLongitudeE6();
		int destLat = dest.getLatitudeE6();
		int destLong = dest.getLongitudeE6();

		distance = EARTH_RADIUS_METERS
				* (PI / 2 - Math.asin(
						Math.sin(destLat) * Math.sin(sourceLat)
						+ Math.cos(destLong - sourceLong) * Math.cos(destLat)
						* Math.cos(sourceLat)));
		
		return distance;
	}
}
