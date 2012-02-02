package org.agetac.tabs;

import org.agetac.controller.Controller;
import org.agetac.observer.MyObservable;
import org.agetac.tabs.sign.ITabActivity;

import android.app.Activity;

public abstract class MyActivity extends Activity implements ITabActivity {

	protected Controller controller;
	protected MyObservable observable;
	
	@Override
	protected void onResume() {
		super.onResume();
		controller = Controller.getInstance();
		observable = new MyObservable();
		observable.addObserver(controller);
		controller.addTabActivity(getClass().getSimpleName(), this);
	}
}
