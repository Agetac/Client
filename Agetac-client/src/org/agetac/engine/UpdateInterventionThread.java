package org.agetac.engine;

import org.agetac.common.api.InterventionApi;
import org.agetac.common.exception.BadResponseException;
import org.agetac.common.model.impl.Intervention;

public class UpdateInterventionThread extends Thread {
	
	private static final String TAG = "UpdateInterventionThread";

	private InterventionApi iConn;
	private InterventionEngine engine;
	private boolean doRun = true;
	
	public UpdateInterventionThread(InterventionEngine engine, InterventionApi iConn) {
		this.engine = engine;
		this.iConn = iConn;
	}
	
	@Override
	public void run() {	
		while (doRun) {
			try {
				
				Intervention intervention = iConn.getIntervention();
				engine.updateIntervention(intervention);
				
				// TODO permettre de modifier l'intervalle de MAJ dans les prefs
				sleep(5000);
				
			} catch (BadResponseException e1) {
				android.util.Log.d(TAG, "BadResponse: "+e1.getMessage());
				
			} catch (InterruptedException e) {
				android.util.Log.e(TAG, "Error thread sleep: "+e.getMessage());
			}
		}
		android.util.Log.d(TAG, "UpdateThread stopped");
	}
	
	public void doStop() {
		doRun = false;
	}
}
