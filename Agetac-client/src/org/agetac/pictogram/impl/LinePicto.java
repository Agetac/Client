package org.agetac.pictogram.impl;

import org.agetac.pictogram.sign.IPictogram;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class LinePicto implements IPictogram {
	
	private Point start,stop;
	private int zoomLevelRef;
	private Bitmap bmp;
	private String name;
	private Color color;
	private State state;
	private Shape shape;
	private GraphicalOverload graphicalOverload;
	Paint paint;
	
	public LinePicto(String name, Bitmap bmp, Point start, Point stop, int zoomLevelRef) {
		this.start = start;
		this.stop = stop;
		this.zoomLevelRef = zoomLevelRef;
		this.color = null;
		this.state = null;
		this.shape = null;
		this.graphicalOverload = null;
		this.name = name;
		this.bmp = bmp;
		this.paint = new Paint();
	}
	public LinePicto(String name, Bitmap bmp, Color color, State state, Shape shape, GraphicalOverload graphicalOverload, Point start, Point stop, int zoomLevelRef) {
		this.start = start;
		this.stop = stop;
		this.zoomLevelRef = zoomLevelRef;
		this.color = color;
		this.state = state;
		this.shape = shape;
		this.graphicalOverload = graphicalOverload;
		this.name = name;
		this.bmp = bmp;
		this.paint = new Paint();
	}
	
	public void setStart(Point start) {
		this.start = start;
	}
	
	public void setStop(Point stop) {
		this.stop = stop;
	}
	
	public void setZoomLevelRef(int zoomLevelRef) {
		this.zoomLevelRef = zoomLevelRef;
	}
	
	public Point getStart() {
		return this.start;
	}
	
	public Point setStop() {
		return this.stop;
	}
	
	public int setZoomLevelRef() {
		return this.zoomLevelRef;
	}
	
	@Override
	public Bitmap getBitmap() {
		return bmp;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public GraphicalOverload getGraphicalOverload() {
		return graphicalOverload;
	}

	@Override
	public void draw(Canvas canvas, Point p, boolean shadow,int zoomLevel) {
		float s = zoomLevel/(float)zoomLevelRef;
		canvas.drawLine(p.x+s*start.x, p.y+s*start.y, p.x+s*stop.x, p.y+s*stop.y, paint);
		canvas.drawLine(p.x+s*stop.x, p.y+s*stop.y, p.x+s*(stop.x+(start.x-stop.x)/16+(start.y-stop.y)/16), p.y+s*(stop.y+(start.y-stop.y)/16+(-start.x+stop.x)/16), paint);
		canvas.drawLine(p.x+s*stop.x, p.y+s*stop.y, p.x+s*(stop.x+(start.x-stop.x)/16-(start.y-stop.y)/16), p.y+s*(stop.y+(start.y-stop.y)/16-(-start.x+stop.x)/16), paint);
	}

}
