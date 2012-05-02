package org.agetac.entity;

import java.util.ArrayList;

import org.agetac.common.dto.IModel;

public class EntityList extends ArrayList<IEntity> {

	private static final long serialVersionUID = 6192402122463398955L;

	/**
	 * Permet de récupérer une entitée en fonction d'un id et d'un model
	 * @param uniqueID l'id du model de l'entitée
	 * @param theModel le type du model de l'entitée
	 * @return l'entitée ou null si elle n'existe pas
	 */
	public <T extends IModel> IEntity find(long uid, Class<T> theModel) {
		for (int i=0; i<size(); i++) {
			if (get(i).getModel().getClass() == theModel)  {
				if (get(i).getModel().getId() == (uid)) {
					return get(i);
				}
			}
		}
		
		return null;
	}
}
