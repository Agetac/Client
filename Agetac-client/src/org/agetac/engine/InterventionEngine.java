package org.agetac.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.agetac.engine.sign.IInterventionEngine;
import org.agetac.entity.sign.IEntity;
import org.agetac.model.impl.Intervention;
import org.agetac.model.impl.Vehicule;
import org.agetac.observer.MyObservable;

public class InterventionEngine implements IInterventionEngine {

	private static final String TAG = "InterventionEngine";
	
	private Intervention intervention;
	private List<IEntity> entities;
	private MyObservable observable;
	
	public InterventionEngine(Intervention intervention) {
		this.intervention = intervention;
		observable = new MyObservable();
		entities = new ArrayList<IEntity>();
	}
	
	@Override
	public void addEntity(IEntity entity) {
		if (entity.getModel() instanceof Vehicule) {
			intervention.getVehicules().add((Vehicule) entity.getModel());
			entities.add(entity);
		}
		notifyObservers();
	}

	@Override
	public void removeEntity(IEntity entity) {
		if (entity.getModel() instanceof Vehicule) {
			intervention.getVehicules().remove((Vehicule) entity.getModel());
			entities.remove(entity);
		}
		notifyObservers();
	}

	@Override
	public void editEntity(String uid, IEntity entity) {
		android.util.Log.d(TAG, "not implemented yet");
	}

	@Override
	public List<IEntity> getEntities() {
		return entities;
	}
	
	public Intervention getIntervention() {
		return intervention;
	}
	
	private void notifyObservers() {
		observable.setChanged();
		observable.notifyObservers(intervention);
	}

	public void addObserver(Observer observer) {
		observable.addObserver(observer);
	}
}
