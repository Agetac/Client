package org.agetac.overlay;

import java.util.ArrayList;
import java.util.List;

import org.agetac.overlay.sign.IOverlayItem;
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
	
	private List<IOverlayItem> items;
	private Paint itemPaint;

	public MapOverlay(Context context) {
		super(context);
		items = new ArrayList<IOverlayItem>();
		itemPaint = new Paint();
		itemPaint.setColor(Color.RED);
		itemPaint.setAntiAlias(true);
	}
	
	@Override
	public boolean onLongPress(MotionEvent e, MapView mapView) {
		android.util.Log.d(TAG, "onLongPress(Overlay): "+e.getX()+", "+e.getY());
		IGeoPoint geoP = mapView.getProjection().fromPixels(e.getX(), e.getY());
		for (int i=0; i<items.size(); i++) {
			if (items.get(i).isCloseTo(geoP)) {
				// ask for delete
				android.util.Log.d(TAG, "ASK FOR DELETE");
			}
		}
		return false;
	}

	@Override
	protected void draw(Canvas canvas, MapView mapV, boolean shadow) {
		IOverlayItem item;
		Point p;
		Bitmap bmp;
		
		for (int i=0; i<items.size(); i++) {
			item = items.get(i);
			p = mapV.getProjection().toMapPixels(item.getGeoPoint(), null);
			bmp = items.get(i).getPictogram().getBitmap();
			canvas.drawBitmap(bmp, p.x-(bmp.getWidth()/2), p.y-(bmp.getHeight()/2), null);
		}
	}
	
	public void addItem(IOverlayItem item) {
		items.add(item);
	}
}
