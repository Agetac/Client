package org.agetac.engine;


public class UpdateInterventionThread extends Thread {
	
	private static final String TAG = "UpdateInterventionThread";

	private InterventionEngine engine;
	private boolean doRun = true;
	
	public UpdateInterventionThread(InterventionEngine engine) {
		this.engine = engine;
	}
	
	@Override
	public void run() {	
		while (doRun) {
			// TODO permettre de modifier l'intervalle de MAJ dans les prefs
			try {
				engine.updateIntervention();
				sleep(5000);
				
			} catch (InterruptedException e) {
				android.util.Log.e(TAG, e.getMessage());
			
			} catch (Exception e) {
				android.util.Log.e(TAG, "Exception on update: "+e.getMessage());
			}
		}
		android.util.Log.d(TAG, "UpdateThread stopped");
	}
	
	public void doStop() {
		doRun = false;
	}
}
