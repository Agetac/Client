package org.agetac.tabs;

import org.agetac.R;
import org.agetac.controller.Controller;
import org.agetac.model.ActionFlag;
import org.agetac.model.IEntity;
import org.agetac.observer.MyObservable;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class SITACActivity extends MapActivity implements ITabActivity {
	// Overlay en implements
	
	private Controller controller;
	private MyObservable observable;
	private MapView mapView;
	private String[] data = {
			"Pictogramme 1",
			"Pictogramme 2",
			"Pictogramme 3",
			"Pictogramme 4",
			"Pictogramme 5",
			"Pictogramme 6",
			"Pictogramme 7",
			"Pictogramme 8",
			"Pictogramme 9",
			"Pictogramme 10"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitac);
		
		ListView listView = (ListView) findViewById(R.id.menu);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
		
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
