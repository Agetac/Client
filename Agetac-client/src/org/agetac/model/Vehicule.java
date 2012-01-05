package org.agetac.model;

public class Vehicule implements IEntity {

	private String name;
	private boolean deBase;
	
	public Vehicule(String name, boolean deBase) {
		this.name = name;
		this.deBase = deBase;
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
}
