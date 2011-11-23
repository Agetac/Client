package org.agetac.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import org.agetac.observer.MyObservable;

public class Intervention {

	private List<Vehicule> vehicules;
	private MyObservable observable;
	private static Intervention intervention;
	
	private Intervention() {
		observable = new MyObservable();
		vehicules = new ArrayList<Vehicule>();
		Random gen = new Random();
		int size = gen.nextInt(3);
		for (int i=0; i<size; i++) {
			vehicules.add(new Vehicule("Vehicule de base "+i, true));
		}
	}
	
	public static Intervention getIntervention() {
		if (intervention == null) intervention = new Intervention();
		return intervention;
	}
	
	public static void destroy() {
		intervention = null;
	}
	
	/**
	 * Ajoute un vehicule à l'intervention
	 * @param vehicule à ajouter; ne fait rien si null
	 */
	public void addVehicule(Vehicule vehicule) {
		if (vehicule != null) {
			vehicules.add(vehicule);
			observable.setChanged();
			observable.notifyObservers(vehicules);
		}
	}
	
	/**
	 * Retire un véhicule de l'intervention
	 * @param vehicule à supprimer; ne fait rien si null
	 */
	public void removeVehicule(Vehicule vehicule) {
		boolean isRemoved = vehicules.remove(vehicule);
		if (isRemoved) {
			observable.setChanged();
			observable.notifyObservers(vehicules);
		}
	}
	
	/**
	 * Ajouter ça aux observers pour qu'ils soient notifiés
	 * de tout changement sur l'intervention
	 * @return observable MyObservable object
	 */
	public Observable getObservable() {
		return observable;
	}
	
	public void update() {
		if (!vehicules.isEmpty()) {
			observable.setChanged();
			observable.notifyObservers(vehicules);
		}
	}
}
