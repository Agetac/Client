package org.agetac.tabs;

import org.agetac.R;
import org.agetac.controller.Controller;
import org.agetac.model.ActionFlag;
import org.agetac.model.sign.IEntity;
import org.agetac.observer.MyObservable;
import org.agetac.tabs.sign.ITabActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

public class SITACActivity extends Activity implements ITabActivity {
	// Overlay en implements
	
	private Controller controller;
	private MyObservable observable;
	private MapView mapView;
	private MapController mapCtrl;
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
		mapCtrl = mapView.getController();
		GeoPoint geoP = new GeoPoint(48096397, -1743137);
		mapCtrl.setCenter(geoP);
		mapCtrl.setZoom(16);
		mapView.setBuiltInZoomControls(true);
		
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
