package org.agetac.model;

public class Vehicule {

	private String name;
	private boolean deBase;
	
	public Vehicule(String name, boolean deBase) {
		this.name = name;
		this.deBase = deBase;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public boolean isDeBase() {
		return deBase;
	}
}
