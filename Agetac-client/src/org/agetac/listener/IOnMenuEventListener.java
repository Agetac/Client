package org.agetac.listener;

import org.agetac.entity.IEntity;
import org.agetac.view.MenuGroup;

public interface IOnMenuEventListener {

	/**
	 * Appelée quand le menu est caché
	 */
	public void onHideMenu();
	
	/**
	 * Appelé quand le menu est affiché
	 */
	public void onShowMenu();
	
	/**
	 * Appelé quand un item du menu a été sélectionné
	 * @param e l'entitée sélectionnée dans le menu
	 */
	public void onEntitySelected(IEntity e);
}
