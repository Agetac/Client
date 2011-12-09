package org.agetac.tabs;

import org.agetac.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class SITACActivity extends MapActivity implements OnClickListener {
	
	private MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitac);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);
	}


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onClick(View v) {
		
	}
}
