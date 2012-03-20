package org.agetac.engine;

import java.util.List;

import org.agetac.common.api.InterventionApi;
import org.agetac.common.exception.BadResponseException;
import org.agetac.common.model.impl.Vehicule;

import android.content.Context;

public class UpdateInterventionThread extends Thread {
	
	private static final String TAG = "UpdateInterventionThread";

	private InterventionApi iConn;
	private InterventionEngine engine;
	private Context context;
	private boolean doRun = true;
	
	public UpdateInterventionThread(InterventionEngine engine, InterventionApi iConn, Context c) {
		this.engine = engine;
		this.iConn = iConn;
		this.context = c;
	}
	
	@Override
	public void run() {
		while (doRun) {
			try {
				List<Vehicule> vList = iConn.getVehicules();
				for (int i=0; i<vList.size(); i++) {
					android.util.Log.d(TAG, "> "+vList.get(i).toString());
				}
				
			} catch (BadResponseException e1) {
				android.util.Log.d(TAG, "BadResponse: "+e1.getMessage());
			}
			
			
			try {
				// TODO permettre de modifier l'intervalle de MAJ dans les prefs
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void doStop() {
		doRun = false;
	}
}
