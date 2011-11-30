package org.agetac.memento;

import org.agetac.model.Entity;

public class AddEntityMemento implements IMemento {

	public Entity entity;
	
	public void setEntity(Entity e) {
		entity = e;
	}
	
	public Entity getEntity() {
		return entity;
	}
}
