package org.agetac.pictogram.impl;

import org.agetac.pictogram.sign.IPictogram;

import android.graphics.Bitmap;

public class Pictogram implements IPictogram {

	private Bitmap bmp;
	private String name;
	
	public Pictogram(String name, Bitmap bmp) {
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
}
