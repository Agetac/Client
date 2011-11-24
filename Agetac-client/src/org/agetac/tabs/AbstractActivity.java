package org.agetac.tabs;

import java.util.Observer;

import org.agetac.R;
import org.agetac.model.Intervention;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public abstract class AbstractActivity extends Activity implements Observer {
	
	protected Intervention intervention;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		System.out.println("FUCK ME");
		// l'activité observe l'intervention
		intervention = Intervention.getIntervention();
		intervention.addObserver(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		intervention.update();
	}
	
	/**
	 * Ne pas redifinir cette méthode sans appeler super.onBackPressed()
	 * car elle définit déjà un moyen dans AbstractActivity de confirmer
	 * la fermeture de l'activitée en cours. cf. AbstractFactory.java
	 */
	@Override
	public void onBackPressed() {
		AlertDialog.Builder confirmExitDialog = new AlertDialog.Builder(this);
		confirmExitDialog.setTitle(R.string.dialog_title_confirmexit);
		confirmExitDialog.setMessage(R.string.dialog_confirmexit);
		confirmExitDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intervention.destroy();
				setResult(RESULT_OK);
				finish();
			}
		});
		confirmExitDialog.setNegativeButton(R.string.no, null);
		confirmExitDialog.show();
	}
}
