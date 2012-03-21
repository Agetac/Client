package org.agetac.entity;

import java.util.ArrayList;

import org.agetac.common.model.sign.IModel;

public class EntityList extends ArrayList<IEntity> {

	/**
	 * Return an entity that matches with the id
	 * @param uniqueID
	 * @return
	 */
	public <T extends IModel> IEntity find(String uniqueID, Class<T> theModel) {
		for (int i=0; i<size(); i++) {
			if (get(i).getModel().getClass() == theModel)  {
				if (get(i).getModel().getUniqueID().equals(uniqueID)) {
					return get(i);
				}
			}
		}
		
		return null;
	}
}
