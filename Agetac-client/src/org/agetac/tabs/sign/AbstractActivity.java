package org.agetac.tabs.sign;

import org.agetac.common.ActionFlag;
import org.agetac.controller.Controller;
import org.agetac.entity.sign.IEntity;
import org.agetac.observer.MyObservable;

import android.app.Activity;

public abstract class AbstractActivity extends Activity implements ITabActivity {

	protected Controller controller;
	protected MyObservable observable;
	protected ActionFlag flag;
	protected IEntity touchedEntity;
	
	@Override
	protected void onResume() {
		super.onResume();
		controller = Controller.getInstance();
		observable = new MyObservable();
		observable.addObserver(controller);
		controller.addTabActivity(getClass().getSimpleName(), this);
	}
	
	@Override
	public ActionFlag getActionFlag() {
		return flag;
	}
	
	@Override
	public IEntity getTouchedEntity() {
		return touchedEntity;
	}
	
	@Override
	public String getMessage() {
		return null;
	}
}
