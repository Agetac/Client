package org.agetac.listener;

import org.agetac.overlay.sign.IOverlayItem;
import org.osmdroid.views.MapView;

import android.view.MotionEvent;

public interface IOnOverlayEventListener {

	public void onItemLongPressed(IOverlayItem item);
	
	public void onOverlayLongPressed(MotionEvent event, MapView mapView);
}
