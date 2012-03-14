package org.agetac.engine;

import java.util.List;
import java.util.Observer;

import org.agetac.entity.IEntity;
import org.agetac.model.impl.Intervention;

public interface IInterventionEngine {

	public void addEntity(IEntity entity);
	
	public void removeEntity(IEntity entity);
	
	public void editEntity(IEntity entity);
	
	public Intervention getIntervention();
	
	public void addObserver(Observer o);

	public List<IEntity> getEntities();
}
