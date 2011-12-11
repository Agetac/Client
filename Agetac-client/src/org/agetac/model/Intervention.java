package org.agetac.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Random;

import org.agetac.observer.MyObservable;

public class Intervention {

	private List<Entity> entities;
	private MyObservable observable;
	private static Intervention intervention;
	
	private Intervention() {
		observable = new MyObservable();
		entities = new ArrayList<Entity>();
	}
	
	public static Intervention getInstance() {
		if (intervention == null) intervention = new Intervention();
		return intervention;
	}
	
	public static void destroy() {
		intervention = null;
	}
	
	/**
	 * Ajoute une entité à l'intervention
	 * @param entité à ajouter; ne fait rien si null
	 */
	public void addEntity(Entity entity) {
		if (entity != null) {
			entities.add(entity);
			observable.setChanged();
			observable.notifyObservers(this);
		}
	}
	
	/**
	 * Retire une entité de l'intervention
	 * @param entity à supprimer; ne fait rien si null
	 */
	public void removeEntity(Entity entity) {
		boolean isRemoved = entities.remove(entity);
		if (isRemoved) {
			observable.setChanged();
			observable.notifyObservers(this);
		}
	}
	
	/**
	 * Ajoute un observeur à l'intervention
	 * @param obs un observeur
	 */
	public void addObserver(Observer obs) {
		observable.addObserver(obs);
	}
	
	/**
	 * @return la liste d'entité
	 */
	public List<Entity> getEntities() {
		return entities;
	}
}
