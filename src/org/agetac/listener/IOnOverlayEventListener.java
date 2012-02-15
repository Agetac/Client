package org.agetac.listener;

import org.agetac.overlay.sign.IOverlayItem;
import org.osmdroid.views.MapView;

import android.graphics.Point;
import android.view.MotionEvent;

public interface IOnOverlayEventListener {

	public void onItemLongPressed(final IOverlayItem item);
	
	public void onOverlayLongPressed(final MotionEvent event, final MapView mapView);
}
