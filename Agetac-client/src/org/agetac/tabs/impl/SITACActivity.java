package org.agetac.tabs.impl;

import org.agetac.R;
import org.agetac.model.ActionFlag;
import org.agetac.model.sign.IEntity;
import org.agetac.tabs.MyActivity;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SITACActivity extends MyActivity {
	// Overlay en implements
	
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
		GeoPoint geoP = new GeoPoint(48115562, -1638084);
		mapCtrl.setCenter(geoP);
		mapCtrl.setZoom(12);
		mapView.setBuiltInZoomControls(true);
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
