package org.agetac.overlay;

import java.util.ArrayList;
import java.util.List;

import org.agetac.entity.sign.IEntity;
import org.agetac.listener.IOnOverlayEventListener;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

public class MapOverlay extends Overlay {
	
	private static final String TAG = "MapOverlay";
	
	private List<IEntity> items;
	private Paint itemPaint;
	private IOnOverlayEventListener listener;
	private int precision;

	public MapOverlay(Context context) {
		super(context);
		items = new ArrayList<IEntity>();
		itemPaint = new Paint();
		itemPaint.setColor(Color.RED);
		itemPaint.setAntiAlias(true);
		
		// TODO permettre à l'utilisateur de pouvoir modifier la precision dans
		// les paramètres de l'application
		precision = 5;
	}
	
	@Override
	public boolean onLongPress(MotionEvent e, MapView mapView) {
		IGeoPoint clickedP = mapView.getProjection().fromPixels(e.getX(), e.getY());

		for (int i=0; i<items.size(); i++) {
			if (items.get(i).isCloseTo(clickedP, precision)) {
				if (listener != null) {
					listener.onEntityLongPressed(items.get(i));
					return true;
				}
			}
		}
		
		if (listener != null) {
			listener.onOverlayLongPressed(e, mapView);
			return true;
		}
		
		return false;
	}

	@Override
	protected void draw(Canvas canvas, MapView mapV, boolean shadow) {
		IEntity item;
		Point p;
		Bitmap bmp;
		
		for (int i=0; i<items.size(); i++) {
			item = items.get(i);
			p = mapV.getProjection().toMapPixels(item.getGeoPoint(), null);
			bmp = items.get(i).getPictogram().getBitmap();
			canvas.drawBitmap(bmp, p.x-(bmp.getWidth()/2), p.y-(bmp.getHeight()/2), null);
		}
	}
	
	public void addEntities(List<IEntity> entities) {
		items.clear();
		items.addAll(entities);
	}
	
	public void addEntity(IEntity entity) {
		items.add(entity);
	}
	
	public void setOnOverlayEventListener(IOnOverlayEventListener listener) {
		this.listener = listener;
	}
	
	public void removeOnOverlayEventListener() {
		this.listener = null;
	}
}
