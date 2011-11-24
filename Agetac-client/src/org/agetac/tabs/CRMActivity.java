package org.agetac.tabs;

import java.util.Observable;
import java.util.Observer;

import org.agetac.R;
import org.agetac.model.Intervention;
import org.agetac.model.Vehicule;

import android.os.Bundle;
import android.widget.Button;

public class CRMActivity extends AbstractActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crm);
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub

	}
}
