package org.agetac.engine;

import java.util.List;
import java.util.Observer;

import org.agetac.common.model.impl.Intervention;
import org.agetac.common.model.impl.Message;
import org.agetac.entity.IEntity;

public interface IInterventionEngine {

	/**
	 * Ajoute une entitee a l'intervention
	 * et envoi la modif au server
	 * @param entity
	 */
	public void addEntity(IEntity entity);
	
	/**
	 * Supprime une entitee de l'intervention
	 * et envoi la modif au server
	 * @param entity
	 */
	public void removeEntity(IEntity entity);
	
	/**
	 * Met a jour une entitee et envoi la modif au server
	 * @param entity
	 */
	public void editEntity(IEntity entity);
	
	/**
	 * Met a jour toute l'intervention en faisant un diff
	 * avec celle passee en parametre
	 * Cette methode est appelee par le thread d'update
	 * (UpdateInterventionThread)
	 * @param inter
	 */
	public void updateIntervention(Intervention inter);
	
	/**
	 * Ajoute un message a l'intervention
	 * et envoi la modif au server
	 * @param m
	 * @return 
	 */
	public boolean sendMessage(Message m); 
	
	/**
	 * L'intervention actuelle
	 * @return intervention l'intervention en cours
	 */
	public Intervention getIntervention();
	
	/**
	 * Ajoute un observeur
	 * @param o
	 */
	public void addObserver(Observer o);

	/**
	 * La liste des entitees de l'intervention
	 * @return entities liste des entitees
	 */
	public List<IEntity> getEntities();

	/**
	 * Demande au thread de MAJ de l'intervention
	 * de s'arreter des que possible
	 */
	public void stopUpdates();
}
