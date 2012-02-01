package org.agetac.model;

import org.agetac.model.sign.IEntity;
import org.agetac.pictogram.sign.IPictogram;
import org.osmdroid.util.GeoPoint;

public class Vehicule implements IEntity {

	private String name;
	private boolean deBase;
	private GeoPoint location;
	private IPictogram picto;
	
	public Vehicule(String name, boolean deBase) {
		this.name = name;
		this.deBase = deBase;
		location = null;
	}
	
	public Vehicule(String name, boolean deBase, GeoPoint m) {
		this.name = name;
		this.deBase = deBase;
		location = m;
	}
	
	
	public Vehicule(String name, boolean deBase, GeoPoint m, IPictogram p) {
		this.name = name;
		this.deBase = deBase;
		location = m;
		picto = p;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public boolean isDeBase() {
		return deBase;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public GeoPoint getLocation() {
		return location;
	}

	@Override
	public IPictogram getPicto() {
		return picto;
	}
}
