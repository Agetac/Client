package org.agetac.pictogram.impl;

import org.agetac.pictogram.sign.IPictogram;

import android.graphics.Bitmap;

public class Pictogram implements IPictogram {

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
	public Pictogram(String name, Bitmap bmp, Color color, State state, Shape shape, GraphicalOverload graphicalOverload) {
		this.color = color;
		this.state = state;
		this.shape = shape;
		this.graphicalOverload = graphicalOverload;
		this.name = name;
		this.bmp = bmp;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Bitmap getBitmap() {
		return bmp;
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
}
