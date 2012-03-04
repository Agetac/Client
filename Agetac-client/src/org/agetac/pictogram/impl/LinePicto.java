package org.agetac.pictogram.impl;

import org.agetac.pictogram.sign.IPictogram;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class LinePicto implements IPictogram {
	
	private Point start,stop;
	private Bitmap bmp;
	private String name;
	private Color color;
	private State state;
	private Shape shape;
	private GraphicalOverload graphicalOverload;
	Paint paint;
	
	public LinePicto(String name, Bitmap bmp, Point start, Point stop) {
		this.start = start;
		this.stop = stop;
		this.color = null;
		this.state = null;
		this.shape = null;
		this.graphicalOverload = null;
		this.name = name;
		this.bmp = bmp;
		this.paint = new Paint();
	}
	public LinePicto(String name, Bitmap bmp, Color color, State state, Shape shape, GraphicalOverload graphicalOverload, Point start, Point stop) {
		this.start = start;
		this.stop = stop;
		this.color = color;
		this.state = state;
		this.shape = shape;
		this.graphicalOverload = graphicalOverload;
		this.name = name;
		this.bmp = bmp;
		this.paint = new Paint();
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
	public void draw(Canvas canvas, Point p, boolean shadow) {
		canvas.drawLine(p.x+start.x, p.y+start.y, p.x+stop.x, p.y+stop.y, paint);
		canvas.drawLine(p.x+stop.x, p.y+stop.y, p.x+stop.x+(start.x-stop.x)/16+(start.y-stop.y)/16, p.y+stop.y+(start.y-stop.y)/16+(start.x+stop.x)/16, paint);
		canvas.drawLine(p.x+stop.x, p.y+stop.y, p.x+stop.x+(start.x-stop.x)/16-(start.y-stop.y)/16, p.y+stop.y+(start.y-stop.y)/16-(start.x+stop.x)/16, paint);
	}

}
