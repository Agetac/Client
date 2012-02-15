package org.agetac.listener;

import org.agetac.entity.sign.IEntity;
import org.osmdroid.views.MapView;

import android.view.MotionEvent;

public interface IOnOverlayEventListener {

	public void onEntityLongPressed(final IEntity entity);
	
	public void onOverlayLongPressed(final MotionEvent event, final MapView mapView);
}
