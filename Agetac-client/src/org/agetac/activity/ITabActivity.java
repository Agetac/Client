package org.agetac.activity;

import org.agetac.controller.Controller.ActionFlag;
import org.agetac.entity.IEntity;

public interface ITabActivity {

	public ActionFlag getActionFlag();
	
	public IEntity getTouchedEntity();
	
	public String getMessage();
	
	/**
	 * Appele lorsque le controleur a reçu une notification
	 * du moteur (on doit effectuer des mises à jour ici)
	 */
	public void update();
}
