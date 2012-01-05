package org.agetac.tabs;

import org.agetac.R;
import org.agetac.controller.Controller;
import org.agetac.model.ActionFlag;
import org.agetac.model.IEntity;
import org.agetac.observer.MyObservable;

import android.app.Activity;
import android.os.Bundle;

public class CRMActivity extends Activity implements ITabActivity {

	private Controller controller;
	private MyObservable observable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crm);
		
		controller = Controller.getInstance();
		controller.addTabActivity(this);
		observable = new MyObservable();
		observable.addObserver(controller);
	}

	@Override
	public ActionFlag getActionFlag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEntity getTouchedEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
