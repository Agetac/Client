package org.agetac.controller;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.agetac.command.impl.AddEntityCommand;
import org.agetac.command.impl.RemoveEntityCommand;
import org.agetac.command.impl.SendMessageCommand;
import org.agetac.command.sign.ICommand;
import org.agetac.controller.impl.CRMController;
import org.agetac.controller.impl.MessagesController;
import org.agetac.controller.impl.MoyensController;
import org.agetac.controller.impl.SITACController;
import org.agetac.controller.impl.SOEICController;
import org.agetac.controller.sign.ISubController;
import org.agetac.entity.sign.IEntity;
import org.agetac.model.impl.Intervention;
import org.agetac.tabs.impl.CRMActivity;
import org.agetac.tabs.impl.MessagesActivity;
import org.agetac.tabs.impl.MoyensActivity;
import org.agetac.tabs.impl.SITACActivity;
import org.agetac.tabs.impl.SOEICActivity;
import org.agetac.tabs.sign.ITabActivity;

public class Controller implements Observer {
	
	private static final String TAG = "Controller";

	private IEntity lastEntity;
	private static Controller controller = new Controller();
	private Map<String, ICommand> commands;
	private ISubController moyensCtrl, sitacCtrl, soeicCtrl, messagesCtrl, crmCtrl;
	private Hashtable<String, ITabActivity> tabs;
	private Intervention intervention;
	private String message;
	
	/**
	 * Constructeur privé pour pattern Singleton.
	 * Empêche la création d'un nouveau controlleur lorsque
	 * celui-ci a déjà été crée une première fois.
	 * 
	 * - Récupère l'unique instance d'Intervention
	 * - Crée la liste de ITabActivity qui contiendra les
	 *   différents onglets de l'application.
	 * - Crée les différents controlleurs associés aux onglets
	 * - Crée toutes les commandes concrètes
	 */
	private Controller() {
		tabs = new Hashtable<String, ITabActivity>();
//		intervention = Intervention.getInstance();
		intervention = new Intervention("inter");
		
		initCommands();
		
		intervention.addObserver(this);
		
//		// XXX test récup agent via serv
//		Agent agent = ServerManager.getInstance().getAgent("ag0");
//		System.out.println(">>>> AGENT: "+agent.toString());
	}
	
	private void initCommands() {
		commands = new Hashtable<String, ICommand>();
		commands.put(AddEntityCommand.NAME, new AddEntityCommand(this));
		commands.put(RemoveEntityCommand.NAME, new RemoveEntityCommand(this));
		commands.put(SendMessageCommand.NAME, new SendMessageCommand(this)); 
	}
	
	public static Controller getInstance() {
		return controller;
	}
	
	public void addTabActivity(String tag, ITabActivity act) {
		tabs.put(tag, act);
		
		if (act instanceof SITACActivity) {
			sitacCtrl = new SITACController(this);
			
		} else if (act instanceof SOEICActivity) {
			soeicCtrl = new SOEICController(this);
			
		} else if (act instanceof MoyensActivity) {
			moyensCtrl = new MoyensController(this);
			
		} else if (act instanceof MessagesActivity) {
			messagesCtrl = new MessagesController(this);
			
		} else if (act instanceof CRMActivity) {
			crmCtrl = new CRMController(this);
		}
		
		// on demande à la vue de se mettre à jour
		act.update();
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
	
	public Intervention getIntervention() {
		return intervention;
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
			// faire des updates sur toutes les vues (tabActivity)
			Enumeration<ITabActivity> enumeration = tabs.elements();
			while (enumeration.hasMoreElements()) {
				enumeration.nextElement().update();
			}
			
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