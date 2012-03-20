package org.agetac.controller;

import java.util.Hashtable;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.agetac.activity.CRMActivity;
import org.agetac.activity.ITabActivity;
import org.agetac.activity.MessagesActivity;
import org.agetac.activity.MoyensActivity;
import org.agetac.activity.SITACActivity;
import org.agetac.activity.SOEICActivity;
import org.agetac.command.AddEntityCommand;
import org.agetac.command.ICommand;
import org.agetac.command.RemoveEntityCommand;
import org.agetac.command.SendMessageCommand;
import org.agetac.common.model.impl.Intervention;
import org.agetac.engine.IInterventionEngine;
import org.agetac.engine.InterventionEngine;
import org.agetac.entity.IEntity;
import org.agetac.network.ServerConnection;

import android.content.Context;

public class Controller implements Observer {
	
	private static final String TAG = "Controller";
	
	public enum ActionFlag {
		ADD,
		REMOVE,
		SEND_MESSAGE	
	}

	private IEntity lastEntity;
	private static Controller controller;
	private Map<String, ICommand> commands;
	private ISubController moyensCtrl, sitacCtrl, soeicCtrl, messagesCtrl, crmCtrl;
	private ITabActivity currentActivity;
	private IInterventionEngine interventionEngine;
	private String message;
	
	/**
	 * Constructeur privé pour pattern Singleton.
	 * Empêche la création d'un nouveau controlleur lorsque
	 * celui-ci a déjà été crée une première fois.
	 * 
	 * - Crée une connexion avec le serveur
	 * - Crée un moteur d'intervention grâce à cette connexion
	 * - Crée la liste de ITabActivity qui contiendra les
	 *   différents onglets de l'application.
	 * - Crée les différents controlleurs associés aux activitées
	 * - Crée toutes les commandes concrètes
	 */
	private Controller(Context c) {
		ServerConnection conn = new ServerConnection(c);
		interventionEngine = new InterventionEngine(conn);
		
		initCommands();
		initControllers();
		
		interventionEngine.addObserver(this);
	}
	
	private void initCommands() {
		commands = new Hashtable<String, ICommand>();
		commands.put(AddEntityCommand.NAME, new AddEntityCommand(this));
		commands.put(RemoveEntityCommand.NAME, new RemoveEntityCommand(this));
		commands.put(SendMessageCommand.NAME, new SendMessageCommand(this)); 
	}
	
	private void initControllers() {
		sitacCtrl = new SITACController(this);
		moyensCtrl = new MoyensController(this);
		messagesCtrl = new MessagesController(this);
//		crmCtrl = new CRMController(this);
//		soeicCtrl = new SOEICController(this);
	}
	
	public static Controller getInstance(Context c) {
		if (controller == null) {
			controller = new Controller(c);
		}
		return controller;
	}
	
	public void setCurrentActivity(ITabActivity act) {
		this.currentActivity = act;		
		// on demande à la vue de se mettre à jour
		act.update();
		System.out.println("Controller: appel update sur ["+act.getClass().getSimpleName().toString()+"]");
	}
	
	public IEntity getLastEntity() {
		return lastEntity;
	}
	
	public void setLastEntity(IEntity e) {
		lastEntity = e;
	}

	public Map<String, ICommand> getCommands() {
		return commands;
	}
	
	public IInterventionEngine getInterventionEngine() {
		return interventionEngine;
	}
	
	public void setMessage(String m){
		message = m;
	}
	
	public String getMessage() {
		return message;
	}
	

	@Override
	public void update(Observable observable, Object data) {
		if (data instanceof Intervention) {
			android.util.Log.d(TAG, "update venant d'Intervention");
			if (currentActivity != null) currentActivity.update();
			
		} else if (data instanceof ITabActivity) {
			ITabActivity act = (ITabActivity) data;
			
			if (data instanceof SITACActivity) {
				android.util.Log.d(TAG, "update venant de SITACActivity");
				sitacCtrl.processUpdate(act);
				
			} else if (data instanceof SOEICActivity) {
				android.util.Log.d(TAG, "update venant de SOEICActivity");
				soeicCtrl.processUpdate(act);
				
			} else if (data instanceof MoyensActivity) {
				android.util.Log.d(TAG, "update venant de MoyensActivity");
				moyensCtrl.processUpdate(act);
				
			} else if (data instanceof MessagesActivity) {
				android.util.Log.d(TAG, "update venant de MessagesActivity");
				messagesCtrl.processUpdate(act);
			
			} else if (data instanceof CRMActivity) {
				android.util.Log.d(TAG, "update venant de CRMActivity");
				crmCtrl.processUpdate(act);
			
			} else {
				android.util.Log.d(TAG, "update non pris en charge! ITabActivity unknown");	
			}
			
		} else {
			android.util.Log.d(TAG, "update non pris en charge! ni une Intervention ni une ITabActivity");
		}
	}
}
