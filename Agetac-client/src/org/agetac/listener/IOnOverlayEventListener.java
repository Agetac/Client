package org.agetac.listener;

import org.agetac.entity.IEntity;
import org.osmdroid.views.MapView;

import android.view.MotionEvent;

public interface IOnOverlayEventListener {

	/**
	 * Appelée quand un item sur la map est sélectionné assez longtemps
	 * @param entity l'entitée selectionnée
	 */
	public void onEntityLongPressed(final IEntity entity);
	
	/**
	 * Appelée quand la map est clické et qu'aucune entitée ne se trouve
	 * à l'endroit sélectionné
	 * @param event l'évenement déclenché
	 * @param mapView la map
	 */
	public void onOverlayLongPressed(final MotionEvent event, final MapView mapView);

	/**
	 * Appelée quand un début de drag-n-drop est effectué
	 * @param event l'evenement
	 * @param mapView la map
	 * @return boolean
	 */
	public boolean lineBegin(final MotionEvent event, final MapView mapView);

	/**
	 * Appelée quand la fin d'un drag-n-drop est détectée
	 * @param end
	 * @param mapView
	 * @return boolean
	 */
	public boolean onUp(final MotionEvent end, final MapView mapView);

}
