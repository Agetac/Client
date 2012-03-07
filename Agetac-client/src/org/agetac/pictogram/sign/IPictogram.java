package org.agetac.pictogram.sign;

import org.agetac.pictogram.impl.Color;
import org.agetac.pictogram.impl.GraphicalOverload;
import org.agetac.pictogram.impl.Shape;
import org.agetac.pictogram.impl.State;
import org.osmdroid.views.MapView.Projection;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;


public interface IPictogram {

	public Bitmap getBitmap();
	
	public String getName();
	
	public Color getColor();
	
	public Shape getShape();
	
	public State getState();
	
	public GraphicalOverload getGraphicalOverload();
	
	public void draw(Canvas canvas, Point p, boolean shadow, Projection mapProjection);
	
}
