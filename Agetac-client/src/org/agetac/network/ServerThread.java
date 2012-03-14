package org.agetac.network;

import org.agetac.common.api.InterventionConnection;
import org.agetac.common.model.impl.DemandeMoyen;

public class ServerThread extends Thread {

	private static final int DELAY = 5000;
	
	private InterventionConnection iConn;
	private DemandeMoyen dMoyen;
	private boolean doRun = true;
	
	/**
	 * Cree un thread qui va demander en boucle au serveur de lui renvoyer
	 * la demande de moyen passé en paramètre et qui va vérifier le changement
	 * d'état de cette demande afin de pouvoir récupérer le vehicule lorsque
	 * celui-ci aura déterminé par le serveur.
	 * 
	 * Lorsque que le serveur aura modifié l'état de la demande de moyen en
	 * "ACCEPTEE" le thread s'arrêtera
	 * @param iConn la connexion avec l'intervention
	 * @param dm la demande de moyen 
	 */
	public ServerThread(InterventionConnection iConn, DemandeMoyen dm) {
		this.iConn = iConn;
		this.dMoyen = dm;
	}
	
	@Override
	public void run() {
		while (doRun) {
			long startTime = System.currentTimeMillis();
			while (startTime+DELAY > System.currentTimeMillis()) {}
//			iConn.getDemandeMoyen(dMoyen.getUniqueID()).getState();
		}
	}
	
	/**
	 * Le thread s'arretera dès que possible
	 */
	public void doStop() {
		this.doRun = false;
	}
}
