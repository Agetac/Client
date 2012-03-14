package org.agetac.entity;

import java.io.Serializable;

import org.agetac.entity.Entity.EntityState;
import org.agetac.model.sign.IModel;
import org.agetac.view.IPictogram;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.MapView;

import android.graphics.Canvas;

public interface IEntity extends Serializable {
	
	public IModel getModel();

	public IPictogram getPictogram();

	public IGeoPoint getGeoPoint();
	
	public boolean isCloseTo(IGeoPoint pt, int precision);
	
	public IEntity clone();

	public EntityState getState();
	
	public void draw(Canvas canvas, MapView mapV, boolean shadow);
}
