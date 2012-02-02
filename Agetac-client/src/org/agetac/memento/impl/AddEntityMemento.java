package org.agetac.memento.impl;

import org.agetac.entity.sign.IEntity;
import org.agetac.memento.sign.IMemento;

public class AddEntityMemento implements IMemento {

	public IEntity entity;
	
	public void setEntity(IEntity e) {
		entity = e;
	}
	
	public IEntity getEntity() {
		return entity;
	}
}
