package org.agetac.tabs.impl;

import org.agetac.R;
import org.agetac.fragment.HiddenMenuFragment;
import org.agetac.fragment.OpenedMenuFragment;
import org.agetac.listener.IOnMenuEventListener;
import org.agetac.listener.IOnOverlayEventListener;
import org.agetac.model.ActionFlag;
import org.agetac.model.sign.IEntity;
import org.agetac.overlay.MapOverlay;
import org.agetac.overlay.OverlayItem;
import org.agetac.overlay.sign.IOverlayItem;
import org.agetac.pictogram.sign.IPictogram;
import org.agetac.tabs.MyActivity;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class SITACActivity extends MyActivity implements IOnMenuEventListener, IOnOverlayEventListener {
	
	private static final String TAG = "SITACACtivity";
	
	private MapView mapView;
	private MapOverlay mapOverlay;
	private MapController mapCtrl;
	private FragmentManager fManager;
	private OpenedMenuFragment openedMenuFrag;
	private HiddenMenuFragment hiddenMenuFrag;
	private IPictogram currentPicto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitac);
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setTileSource(TileSourceFactory.MAPNIK);
		
		mapCtrl = mapView.getController();
		mapCtrl.setZoom(18); // you must call setZoom before setCenter
		
		GeoPoint geoP = new GeoPoint(48115436 ,-1638084);
		mapCtrl.setCenter(geoP);
		
		fManager = getFragmentManager();
		openedMenuFrag = (OpenedMenuFragment) fManager.findFragmentById(R.id.fragment_menu_opened);
		openedMenuFrag.setOnMenuEventListener(this);
		hiddenMenuFrag = (HiddenMenuFragment) fManager.findFragmentById(R.id.fragment_menu_hidden);
		hiddenMenuFrag.setOnMenuEventListener(this);
		fManager.beginTransaction().hide(hiddenMenuFrag).commit();
		
		mapOverlay = new MapOverlay(this);
		mapOverlay.setOnOverlayEventListener(this);
		mapView.getOverlays().add(mapOverlay);
		
		mapView.setBuiltInZoomControls(false);
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

	@Override
	public void onHideMenu() {
		FragmentTransaction fTransac = fManager.beginTransaction();
		fTransac.hide(openedMenuFrag);
		fTransac.show(hiddenMenuFrag);
		fTransac.commit();
	}

	@Override
	public void onShowMenu() {
		FragmentTransaction fTransac = fManager.beginTransaction();
		fTransac.hide(hiddenMenuFrag);
		fTransac.show(openedMenuFrag);
		fTransac.commit();
		openedMenuFrag.startShowMenuAnim();
	}

	@Override
	public void onPictogramSelected(IPictogram p) {
		this.currentPicto = p;
	}

	@Override
	public void onItemLongPressed(final IOverlayItem item) {
		// TODO changer ça
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(SITACActivity.this,
						"Item sélectionné -- "+item.getPictogram().getBitmap().toString(),
						Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public void onOverlayLongPressed(MotionEvent e, MapView mapView) {
		if (currentPicto != null) {
			GeoPoint m = (GeoPoint) mapView.getProjection().fromPixels(e.getX(), e.getY());
			mapOverlay.addItem(new OverlayItem(currentPicto, m.getLatitudeE6(), m.getLongitudeE6()));
			mapView.invalidate();
		}
	}
}
