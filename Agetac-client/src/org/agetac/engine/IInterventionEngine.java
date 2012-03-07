package org.agetac.engine;

import java.util.List;
import java.util.Observer;

import org.agetac.entity.IEntity;

public interface IInterventionEngine {

	public void addEntity(IEntity entity);
	
	public void removeEntity(IEntity entity);
	
	public void editEntity(String uid, IEntity entity);
	
	public void addObserver(Observer o);

	public List<IEntity> getEntities();
}
