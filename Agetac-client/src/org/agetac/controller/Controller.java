package org.agetac.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.agetac.command.AddEntityCommand;
import org.agetac.command.EnvoyerMessageCommand;
import org.agetac.command.ICommand;
import org.agetac.command.RemoveEntityCommand;
import org.agetac.model.Agent;
import org.agetac.model.IEntity;
import org.agetac.model.Intervention;
import org.agetac.model.Vehicule;
import org.agetac.network.ServerManager;
import org.agetac.tabs.CRMActivity;
import org.agetac.tabs.ITabActivity;
import org.agetac.tabs.MessagesActivity;
import org.agetac.tabs.MoyensActivity;
import org.agetac.tabs.SITACActivity;
import org.agetac.tabs.SOEICActivity;

public class Controller implements Observer {
	
	private static final String TAG = "Controller";

	private IEntity lastEntity;
	private static Controller controller = new Controller();
	private Map<String, ICommand> commands;
	private ISubController moyensCtrl, sitacCtrl, soeicCtrl, messagesCtrl, crmCtrl;
	private List<ITabActivity> tabs;
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
		tabs = new ArrayList<ITabActivity>();
		intervention = Intervention.getInstance();
		
		initSubControllers();
		initCommands();
		
		intervention.addObserver(this);
		Random gen = new Random();
		int size = gen.nextInt(3)+1;
		for (int i=0; i<size; i++) {
			intervention.addEntity(new Vehicule("Entité de base "+i, true));
		}
		
//		// XXX test récup agent via serv
//		Agent agent = ServerManager.getInstance().getAgent("ag0");
//		System.out.println(">>>> AGENT: "+agent.toString());
	}
	
	private void initSubControllers() {
		moyensCtrl = new MoyensController(this);
		sitacCtrl = new SITACController(this);
		soeicCtrl = new SOEICController(this);
		messagesCtrl = new MessagesController(this);
		crmCtrl = new CRMController(this);
	}
	
	private void initCommands() {
		commands = new Hashtable<String, ICommand>();
		commands.put(AddEntityCommand.NAME, new AddEntityCommand(this));
		commands.put(RemoveEntityCommand.NAME, new RemoveEntityCommand(this));
		commands.put(EnvoyerMessageCommand.NAME, new EnvoyerMessageCommand(this)); 
	}
	
	public static Controller getInstance() {
		return controller;
	}
	
	public void addTabActivity(ITabActivity act) {
		tabs.add(act);
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
			for (ITabActivity tab : tabs) {
				tab.update();
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
				android.util.Log.d(TAG, "update non pris en charge! act="+act.toString());	
			}
			
		} else {
			android.util.Log.d(TAG, "update non pris en charge! data="+data.toString());
		}
	}
}
