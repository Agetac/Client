package org.agetac.engine.sign;

import org.agetac.entity.sign.IEntity;

public interface IInterventionEngine {

	public void addEntity(IEntity entity);
	
	public void deleteEntity(IEntity entity);
	
	public void editEntity(String uid, IEntity entity);
}
