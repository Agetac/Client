package org.agetac.engine;

import java.util.List;
import java.util.Observer;

import org.agetac.common.model.impl.Intervention;
import org.agetac.common.model.impl.Message;
import org.agetac.entity.IEntity;

public interface IInterventionEngine {

	public void addEntity(IEntity entity);
	
	public void removeEntity(IEntity entity);
	
	public void editEntity(IEntity entity);
	
	public void sendMessage(Message m); 
	
	public Intervention getIntervention();
	
	public void addObserver(Observer o);

	public List<IEntity> getEntities();

	public void stopUpdates();
}
