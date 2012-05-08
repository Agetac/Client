package org.agetac.view;

import org.osmdroid.views.MapView.Projection;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;

public class LinePicto implements IPictogram {
	
	private String id;
	private Point start,stop;
	private float scaleRef;
	private Bitmap bmp;
	private String name;
	private Color color;
	private State state;
	private Shape shape;
	private GraphicalOverload graphicalOverload;
	Paint paint;
	
	public LinePicto(String id, Bitmap bmp, Point start, Point stop, float scaleRef) {
		this.id = id;
		this.start = start;
		this.stop = stop;
		this.scaleRef = scaleRef;
		this.color = null;
		this.state = null;
		this.shape = null;
		this.graphicalOverload = null;
		this.name = id.split("_")[1];
		this.bmp = bmp;
		this.paint = new Paint();
		paint.setColor(android.graphics.Color.BLACK);
	}
	public LinePicto(String id, Bitmap bmp, Color color, State state, Shape shape, GraphicalOverload graphicalOverload, Point start, Point stop, float scaleRef) {
		this.id = id;
		this.start = start;
		this.stop = stop;
		this.scaleRef = scaleRef;
		this.color = color;
		this.state = state;
		this.shape = shape;
		this.graphicalOverload = graphicalOverload;
		this.name = id.split("_")[1];
		this.bmp = bmp;
		this.paint = new Paint();
		handlePaint();
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public void setStart(Point start) {
		this.start = start;
	}
	
	public void setStop(Point stop) {
		this.stop = stop;
	}
	
	public void setscaleRef(float scaleRef) {
		this.scaleRef = scaleRef;
	}
	
	public Point getStart() {
		return this.start;
	}
	
	public Point getStop() {
		return this.stop;
	}
	
	public float getscaleRef() {
		return this.scaleRef;
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
	public void draw(Canvas canvas, Point p, boolean shadow, Projection mapProjection) {
		float s = mapProjection.metersToEquatorPixels(1.0f)/scaleRef;
	
		canvas.drawLine(p.x+s*start.x, p.y+s*start.y, p.x+s*stop.x, p.y+s*stop.y, paint);
		canvas.drawLine(p.x+s*stop.x, p.y+s*stop.y, p.x+s*(stop.x+(start.x-stop.x)/16+(start.y-stop.y)/16), p.y+s*(stop.y+(start.y-stop.y)/16+(-start.x+stop.x)/16), paint);
		canvas.drawLine(p.x+s*stop.x, p.y+s*stop.y, p.x+s*(stop.x+(start.x-stop.x)/16-(start.y-stop.y)/16), p.y+s*(stop.y+(start.y-stop.y)/16-(-start.x+stop.x)/16), paint);
	}
	
	@Override
	public LinePicto clone() {
		return new LinePicto(id, bmp, color, state, shape, graphicalOverload, new Point(start), new Point(stop), scaleRef);
	}
	
	private void handlePaint() {
		paint.setAntiAlias(true);
		paint.setStrokeWidth(3);
		paint.setStyle(Style.STROKE);
		
		switch (color) {
			case BLACK:
				paint.setColor(android.graphics.Color.BLACK);
				break;
				
			case BLUE:
				paint.setColor(android.graphics.Color.BLUE);
				break;
				
			case GREEN:
				paint.setColor(android.graphics.Color.GREEN);
				break;
				
			case ORANGE:
				paint.setColor(android.graphics.Color.rgb(255, 102, 51));
				break;
				
			case RED:
				paint.setColor(android.graphics.Color.RED);
				break;
				
			case VIOLET:
				paint.setColor(android.graphics.Color.rgb(102, 51, 153));
				break;
		}
	}
}
