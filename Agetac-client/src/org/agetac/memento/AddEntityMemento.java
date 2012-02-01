package org.agetac.memento;

import org.agetac.memento.sign.IMemento;
import org.agetac.model.sign.IEntity;

public class AddEntityMemento implements IMemento {

	public IEntity entity;
	
	public void setEntity(IEntity e) {
		entity = e;
	}
	
	public IEntity getEntity() {
		return entity;
	}
}
