package org.agetac.tabs;

import org.agetac.R;
import org.agetac.controller.Controller;
import org.agetac.model.ActionFlag;
import org.agetac.model.IEntity;
import org.agetac.observer.MyObservable;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class SITACActivity extends MapActivity implements ITabActivity {
	
	private Controller controller;
	private MyObservable observable;
	private MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitac);
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);
		
		controller = Controller.getInstance();
		controller.addTabActivity(this);
		observable = new MyObservable();
		observable.addObserver(controller);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
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
