package org.agetac.tabs;

import java.util.Observer;

import org.agetac.R;
import org.agetac.controller.Controller;
import org.agetac.model.Entity;
import org.agetac.model.Intervention;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public abstract class AbstractActivity extends Activity implements Observer {
	
	protected Intervention intervention;
	protected Controller controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// l'activité observe l'intervention
		intervention = Intervention.getInstance();
		intervention.addObserver(this);
		
		// on crée le controlleur
		controller = Controller.getInstance(intervention);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		intervention.callUpdate();
	}
	
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
