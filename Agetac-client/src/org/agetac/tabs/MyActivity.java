package org.agetac.tabs;

import org.agetac.controller.Controller;
import org.agetac.model.ActionFlag;
import org.agetac.model.sign.IEntity;
import org.agetac.observer.MyObservable;
import org.agetac.tabs.sign.ITabActivity;

import android.app.Activity;

public class MyActivity extends Activity implements ITabActivity {

	protected Controller controller;
	protected MyObservable observable;
	
	@Override
	protected void onResume() {
		super.onResume();
		controller = Controller.getInstance();
		observable = new MyObservable();
		observable.addObserver(controller);
		controller.addTabActivity(this);
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
