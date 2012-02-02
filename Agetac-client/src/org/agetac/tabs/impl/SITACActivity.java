package org.agetac.tabs.impl;

import java.util.ArrayList;
import java.util.List;

import org.agetac.R;
import org.agetac.common.ActionFlag;
import org.agetac.common.EtatVehicule;
import org.agetac.entity.impl.VehiculeEntity;
import org.agetac.entity.sign.IEntity;
import org.agetac.fragment.HiddenMenuFragment;
import org.agetac.fragment.OpenedMenuFragment;
import org.agetac.listener.IOnMenuEventListener;
import org.agetac.listener.IOnOverlayEventListener;
import org.agetac.model.impl.Position;
import org.agetac.model.impl.Vehicule;
import org.agetac.overlay.MapOverlay;
import org.agetac.overlay.impl.OverlayItem;
import org.agetac.overlay.sign.IOverlayItem;
import org.agetac.pictogram.sign.IPictogram;
import org.agetac.tabs.sign.AbstractActivity;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SITACActivity extends AbstractActivity implements IOnMenuEventListener, IOnOverlayEventListener {
	
	private static final String TAG = "SITACACtivity";
	
	private MapView mapView;
	private MapOverlay mapOverlay;
	private MapController mapCtrl;
	private FragmentManager fManager;
	private OpenedMenuFragment openedMenuFrag;
	private HiddenMenuFragment hiddenMenuFrag;
	private IPictogram currentPicto;
	
	private ActionFlag flag;
	private IEntity touchedEntity;


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
		
		mapView.setBuiltInZoomControls(true);
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
	public void update() {
		List<IEntity> entities = controller.getIntervention().getEntities();
		List<IOverlayItem> items = new ArrayList<IOverlayItem>();
		for (IEntity e : entities){
			IGeoPoint m = e.getGeoPoint();
			IPictogram p = e.getPictogram();
			if (m != null && p != null) {
				items.add(new OverlayItem(p, m.getLatitudeE6(), m.getLongitudeE6()));
			}
		}
		mapOverlay.addItems(items);
		mapView.invalidate();
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
		android.util.Log.d(TAG, "longPress on an item !");
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
			Position p = new Position(m.getLongitudeE6(), m.getLatitudeE6());
			flag = ActionFlag.ADD;
			Vehicule v = new Vehicule("42", "FPT Janze", p, null, EtatVehicule.PARTIS, null);
			touchedEntity = new VehiculeEntity(v, currentPicto); //TODO vrai relation picto-Entity
			observable.setChanged();
			observable.notifyObservers(SITACActivity.this);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.sitac_context_menu, menu);
		android.util.Log.d(TAG, "onCreateContextMenu");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.delete_overlay_item:
				android.util.Log.d(TAG, "onContextItemSelected _ delete item");
				break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}
