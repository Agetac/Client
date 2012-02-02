package org.agetac.tabs.sign;

import org.agetac.controller.Controller;
import org.agetac.observer.MyObservable;

import android.app.Activity;

public abstract class AbstractActivity extends Activity implements ITabActivity {

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
