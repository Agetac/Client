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
import android.graphics.Point;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

public class SITACActivity extends AbstractActivity implements IOnMenuEventListener, IOnOverlayEventListener, OnMenuItemClickListener {
	
	private static final String TAG = "SITACACtivity";
	
	private MapView mapView;
	private MapOverlay mapOverlay;
	private MapController mapCtrl;
	private FragmentManager fManager;
	private OpenedMenuFragment openedMenuFrag;
	private HiddenMenuFragment hiddenMenuFrag;
	private IPictogram currentPicto;
	private PopupMenu popupMenu;


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
		
		popupMenu = new PopupMenu(SITACActivity.this, findViewById(R.id.menu_anchor));
		Menu menu = popupMenu.getMenu();
		MenuInflater inflater = popupMenu.getMenuInflater();
		inflater.inflate(R.menu.sitac_context_menu, menu);
		popupMenu.setOnMenuItemClickListener(this);
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
		runOnUiThread(new Runnable() {
			@Override
			public void run() {				
				popupMenu.show();
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
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_delete:
			android.util.Log.d(TAG, "TODO: supprimer l'item");
			Toast.makeText(this, "TODO: supprimer l'item", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.menu_item_edit:
			Toast.makeText(this, "TODO: modification de l'item", Toast.LENGTH_SHORT).show();
			android.util.Log.d(TAG, "TODO: modification des items");
			break;
	}
	return super.onContextItemSelected(item);
	}
}
