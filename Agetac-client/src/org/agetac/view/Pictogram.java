package org.agetac.view;

import org.osmdroid.views.MapView.Projection;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Pictogram implements IPictogram {

	private String id;
	private Bitmap bmp;
	private String name;
	private Color color;
	private State state;
	private Shape shape;
	private GraphicalOverload graphicalOverload;
	
	
	public Pictogram(String name, Bitmap bmp) {
		this.color = null;
		this.state = null;
		this.shape = null;
		this.graphicalOverload = null;
		this.name = name;
		this.bmp = bmp;
	}
	public Pictogram(String id, Bitmap bmp, Color color, State state, Shape shape, GraphicalOverload graphicalOverload) {
		this.id = id;
		this.color = color;
		this.state = state;
		this.shape = shape;
		this.graphicalOverload = graphicalOverload;
		this.name = id.split("_")[1];
		this.bmp = bmp;
	}
	
	@Override
	public String getId() {
		return id;
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
	public String toString() {
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
	public void draw(Canvas canvas, Point p, boolean shadow, Projection mapProjection) {
		//android.util.Log.d("PICTOOO",bmp.toString());
		canvas.drawBitmap(bmp, p.x-(bmp.getWidth()/2), p.y-(bmp.getHeight()/2), null);		
	}
	
	@Override
	public Pictogram clone() {
		return this;
	}
}
